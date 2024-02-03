package cn.youngkbt.uac.sys.service.impl;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.youngkbt.core.event.LoginInfoEvent;
import cn.youngkbt.uac.core.constant.AuthConstant;
import cn.youngkbt.uac.sys.mapper.SysLoginLogMapper;
import cn.youngkbt.uac.sys.model.dto.SysUserDto;
import cn.youngkbt.uac.sys.model.po.SysLoginLog;
import cn.youngkbt.uac.sys.service.SysLoginLogService;
import cn.youngkbt.uac.sys.service.SysUserService;
import cn.youngkbt.utils.AddressUtil;
import cn.youngkbt.utils.ServletUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_login_info(操作日志记录)】的数据库操作Service实现
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements SysLoginLogService {
    
    private final SysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recordLoginLog(LoginInfoEvent loginInfoEvent) {
        HttpServletRequest request = loginInfoEvent.getRequest();
        // 获取 IP
        String clientIp = ServletUtil.getClientIp(request);
        String address = AddressUtil.getRealAddressByIp(clientIp);
        // 获取客户端标识
        UserAgent userAgent = UserAgentUtil.parse(ServletUtil.getUserAgent(request));
        // 获取客户端操作系统
        String os = userAgent.getOs().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();

        log.info("用户 {} 在 {} - {} 下 {}", loginInfoEvent.getUsername(), clientIp, address, loginInfoEvent.getMessage());

        SysLoginLog loginLog = SysLoginLog.builder()
                .tenantId(loginInfoEvent.getTenantId())
                .userName(loginInfoEvent.getUsername())
                .loginIp(clientIp)
                .loginLocation(address)
                .browser(browser)
                .os(os)
                .loginTime(new Date())
                .msg(loginInfoEvent.getMessage())
                .build();

        // 日志状态
        if (StringUtils.equalsAny(loginInfoEvent.getStatus(), AuthConstant.LOGIN_SUCCESS, AuthConstant.LOGOUT, AuthConstant.REGISTER)) {
            loginLog.setStatus(1);
        } else if (loginInfoEvent.getStatus().equals(AuthConstant.LOGIN_FAIL)) {
            loginLog.setStatus(0);
        }
        
        baseMapper.insert(loginLog);
        
        // 更新用户的登录记录
        SysUserDto sysUserDto = new SysUserDto();
        sysUserDto.setUserId(loginInfoEvent.getUserId())
                .setUserStatus(1)
                .setLoginIp(clientIp)
                .setLoginDate(new Date());
        
        sysUserService.updateOneByUserId(sysUserDto);
    }
}




