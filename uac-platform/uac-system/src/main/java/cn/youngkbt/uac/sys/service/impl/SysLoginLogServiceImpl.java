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
                .username(loginInfoEvent.getUsername())
                .clientName(loginInfoEvent.getClientName())
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
        SysUserDTO sysUserDTO = new SysUserDTO();
        sysUserDTO.setUsername(loginInfoEvent.getUsername())
                .setUserStatus(1)
                .setLoginIp(clientIp)
                .setLoginTime(new Date());

        sysUserService.updateOneByUserId(sysUserDTO);
    }

    @Override
    public TablePage<SysLoginLogVO> listPage(SysLoginLogDTO sysLoginLogDTO, PageQuery pageQuery) {
        LambdaQueryWrapper<SysLoginLog> wrapper = buildQueryWrapper(sysLoginLogDTO);

        if (Objects.isNull(pageQuery.getOrderRuleList())) {
            wrapper.orderByDesc(SysLoginLog::getLoginId);
        }
        
        Page<SysLoginLog> sysMenuPage = baseMapper.selectPage(pageQuery.buildPage(), wrapper);

        return TablePage.build(sysMenuPage, SysLoginLogVO.class);
    }

    private LambdaQueryWrapper<SysLoginLog> buildQueryWrapper(SysLoginLogDTO sysLoginLogDTO) {
        LambdaQueryWrapper<SysLoginLog> wrapper = Wrappers.<SysLoginLog>lambdaQuery()
                .like(StringUtil.hasText(sysLoginLogDTO.getUsername()), SysLoginLog::getUsername, sysLoginLogDTO.getUsername())
                .eq(StringUtil.hasText(sysLoginLogDTO.getClientName()), SysLoginLog::getClientName, sysLoginLogDTO.getClientName())
                .eq(StringUtil.hasText(sysLoginLogDTO.getBrowser()), SysLoginLog::getBrowser, sysLoginLogDTO.getBrowser())
                .eq(Objects.nonNull(sysLoginLogDTO.getStatus()), SysLoginLog::getStatus, sysLoginLogDTO.getStatus())
                .like(StringUtil.hasText(sysLoginLogDTO.getLoginIp()), SysLoginLog::getLoginIp, sysLoginLogDTO.getLoginIp())
                .like(StringUtil.hasText(sysLoginLogDTO.getLoginLocation()), SysLoginLog::getLoginLocation, sysLoginLogDTO.getLoginLocation());
        if (Objects.nonNull(sysLoginLogDTO.getLoginTime())) {
            wrapper.between(SysLoginLog::getLoginTime, sysLoginLogDTO.getLoginTime().get(0), sysLoginLogDTO.getLoginTime().get(1));
        }

        return wrapper;
    }
}




