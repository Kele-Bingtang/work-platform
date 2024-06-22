package cn.youngkbt.uac.auth.service;

import cn.youngkbt.core.constants.ColumnConstant;
import cn.youngkbt.helper.SpringHelper;
import cn.youngkbt.security.domain.LoginUser;
import cn.youngkbt.tenant.helper.TenantHelper;
import cn.youngkbt.uac.auth.convertor.LoginBOToVOConvertor;
import cn.youngkbt.uac.auth.convertor.LoginDTOToBOConvertor;
import cn.youngkbt.uac.auth.model.dto.LoginUserDTO;
import cn.youngkbt.uac.auth.model.vo.LoginVO;
import cn.youngkbt.uac.auth.strategy.AuthHandler;
import cn.youngkbt.uac.core.bo.LoginSuccessBO;
import cn.youngkbt.uac.core.bo.LoginUserBO;
import cn.youngkbt.uac.core.constant.AuthConstant;
import cn.youngkbt.uac.core.event.LoginInfoEvent;
import cn.youngkbt.uac.core.exception.TenantException;
import cn.youngkbt.uac.core.helper.UacHelper;
import cn.youngkbt.uac.system.model.po.SysApp;
import cn.youngkbt.uac.system.model.po.SysClient;
import cn.youngkbt.uac.system.model.po.SysTenant;
import cn.youngkbt.uac.system.service.SysTenantService;
import cn.youngkbt.utils.ServletUtil;
import cn.youngkbt.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2023/11/13 23:18
 * @note 登录服务
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {

    private final SysTenantService tenantService;

    /**
     * 登录
     */
    public LoginVO login(LoginUserDTO loginUserDTO, SysApp sysApp, SysClient sysClient) {
        LoginUserBO loginUserBO = LoginDTOToBOConvertor.INSTANCE.convert(loginUserDTO, sysApp);
        LoginSuccessBO login = AuthHandler.loginDispatch(loginUserBO, sysClient);
        return LoginBOToVOConvertor.INSTANCE.convert(login);
    }

    /**
     * 退出登录
     */
    public boolean logout() {
        LoginUser loginUser = UacHelper.getLoginUser();
        boolean logout = UacHelper.logout();
        if (logout) {
            LoginInfoEvent loginInfoEvent = LoginInfoEvent.builder()
                    .tenantId(loginUser.getTenantId())
                    .userId(loginUser.getUserId())
                    .username(loginUser.getUsername())
                    .clientName(loginUser.getClientName())
                    .loginTime(LocalDateTime.now())
                    .status(AuthConstant.LOGOUT)
                    .request(ServletUtil.getRequest())
                    .message("退出成功")
                    .build();
            // 发布退出成功事件
            SpringHelper.publishEvent(loginInfoEvent);
        }
        return logout;
    }

    public void checkTenant(String tenantId) {
        // 校验租户是否存在
        if (!StringUtil.hasText(tenantId)) {
            throw new TenantException("该租户不存在");
        }
        // 校验租户模式是否启用
        if (!TenantHelper.isEnable()) {
            return;
        }
        SysTenant sysTenant = tenantService.queryByTenantId(tenantId);
        if (Objects.isNull(sysTenant)) {
            log.info("租户 {} 不存在.", tenantId);
            throw new TenantException("租户不存在");
        }
        if (!ColumnConstant.STATUS_NORMAL.equals(sysTenant.getStatus())) {
            log.info("租户 {} 已被停用.", tenantId);
            throw new TenantException("租户已被停用");
        }
        if (Objects.nonNull(sysTenant.getExpireTime())
                && LocalDateTime.now().isAfter(sysTenant.getExpireTime())) {
            log.info("租户 {} 已超过有效期.", tenantId);
            throw new TenantException("租户已超过有效期");
        }
    }
}
