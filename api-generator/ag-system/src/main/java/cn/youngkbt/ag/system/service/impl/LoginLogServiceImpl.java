package cn.youngkbt.ag.system.service.impl;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.youngkbt.ag.core.constant.AuthConstant;
import cn.youngkbt.ag.core.event.LoginInfoEvent;
import cn.youngkbt.ag.system.mapper.LoginLogMapper;
import cn.youngkbt.ag.system.model.dto.UserDTO;
import cn.youngkbt.ag.system.model.po.LoginLog;
import cn.youngkbt.ag.system.service.LoginLogService;
import cn.youngkbt.ag.system.service.UserService;
import cn.youngkbt.utils.AddressUtil;
import cn.youngkbt.utils.ServletUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author Kele-Bingtang
 * @date 2024-06-22 16:27:53
 * @note 针对表「t_login_log（操作日志记录）」的数据库操作 Service 实现
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {
    
    private final UserService userService;

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

        LoginLog loginLog = LoginLog.builder()
                .username(loginInfoEvent.getUsername())
                .loginIp(clientIp)
                .loginLocation(address)
                .browser(browser)
                .os(os)
                .loginTime(loginInfoEvent.getLoginTime())
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
        UserDTO sysUserDTO = new UserDTO();
        sysUserDTO.setUsername(loginInfoEvent.getUsername())
                .setUserStatus(1)
                .setLoginIp(clientIp)
                .setLoginTime(LocalDateTime.now());

        userService.updateByUsername(sysUserDTO);
    }
}




