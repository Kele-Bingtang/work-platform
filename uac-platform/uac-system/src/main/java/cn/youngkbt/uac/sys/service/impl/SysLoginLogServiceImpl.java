package cn.youngkbt.uac.sys.service.impl;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.youngkbt.core.event.LoginInfoEvent;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.uac.core.constant.AuthConstant;
import cn.youngkbt.uac.sys.mapper.SysLoginLogMapper;
import cn.youngkbt.uac.sys.model.dto.SysLoginLogDTO;
import cn.youngkbt.uac.sys.model.dto.SysUserDTO;
import cn.youngkbt.uac.sys.model.po.SysLoginLog;
import cn.youngkbt.uac.sys.model.vo.SysLoginLogVO;
import cn.youngkbt.uac.sys.service.SysLoginLogService;
import cn.youngkbt.uac.sys.service.SysUserService;
import cn.youngkbt.utils.AddressUtil;
import cn.youngkbt.utils.ServletUtil;
import cn.youngkbt.utils.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

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
                .appId(loginInfoEvent.getAppId())
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
        SysUserDTO sysUserDTO = new SysUserDTO();
        sysUserDTO.setUsername(loginInfoEvent.getUsername())
                .setUserStatus(1)
                .setLoginIp(clientIp)
                .setLoginDate(new Date());

        sysUserService.updateOneByUserId(sysUserDTO);
    }

    @Override
    public TablePage<SysLoginLogVO> listPage(SysLoginLogDTO sysLoginLogDTO, PageQuery pageQuery) {
        LambdaQueryWrapper<SysLoginLog> wrapper = buildQueryWrapper(sysLoginLogDTO);
        Page<SysLoginLog> sysMenuPage = baseMapper.selectPage(pageQuery.buildPage(), wrapper);

        return TablePage.build(sysMenuPage, SysLoginLogVO.class);
    }

    private LambdaQueryWrapper<SysLoginLog> buildQueryWrapper(SysLoginLogDTO sysLoginLogDTO) {
        return Wrappers.<SysLoginLog>lambdaQuery()
                .eq(StringUtil.hasText(sysLoginLogDTO.getUserName()), SysLoginLog::getUserName, sysLoginLogDTO.getUserName())
                .eq(StringUtil.hasText(sysLoginLogDTO.getAppId()), SysLoginLog::getAppId, sysLoginLogDTO.getAppId())
                .eq(StringUtil.hasText(sysLoginLogDTO.getBrowser()), SysLoginLog::getAppId, sysLoginLogDTO.getBrowser())
                .eq(Objects.nonNull(sysLoginLogDTO.getDeviceType()), SysLoginLog::getDeviceType, sysLoginLogDTO.getDeviceType())
                .eq(Objects.nonNull(sysLoginLogDTO.getStatus()), SysLoginLog::getStatus, sysLoginLogDTO.getStatus())
                .eq(Objects.nonNull(sysLoginLogDTO.getDeviceType()), SysLoginLog::getLoginIp, sysLoginLogDTO.getDeviceType())
                .like(Objects.nonNull(sysLoginLogDTO.getLoginIp()), SysLoginLog::getLoginIp, sysLoginLogDTO.getLoginIp())
                .like(Objects.nonNull(sysLoginLogDTO.getLoginLocation()), SysLoginLog::getLoginIp, sysLoginLogDTO.getLoginLocation())
                .orderByAsc(SysLoginLog::getLoginTime);
    }
}




