package cn.youngkbt.uac.auth.service;

import cn.hutool.core.util.ObjectUtil;
import cn.youngkbt.core.constants.ColumnConstant;
import cn.youngkbt.uac.auth.convertor.LoginBOToVOConvertor;
import cn.youngkbt.uac.auth.convertor.LoginDTOToBOConvertor;
import cn.youngkbt.uac.auth.model.dto.LoginUserDto;
import cn.youngkbt.uac.auth.strategy.AuthHandler;
import cn.youngkbt.uac.auth.model.vo.LoginVo;
import cn.youngkbt.uac.core.bo.LoginSuccessBO;
import cn.youngkbt.uac.core.bo.LoginUserBO;
import cn.youngkbt.uac.core.exception.TenantException;
import cn.youngkbt.tenant.helper.TenantHelper;
import cn.youngkbt.uac.sys.model.po.SysApp;
import cn.youngkbt.uac.sys.model.po.SysClient;
import cn.youngkbt.uac.sys.model.po.SysTenant;
import cn.youngkbt.uac.sys.service.SysTenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Kele-Bingtang
 * @date 2023/11/13 23:18
 * @note
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {

    private final SysTenantService tenantService;

    /**
     * 登录
     */
    public LoginVo login(LoginUserDto loginUserDto, SysApp sysApp, SysClient sysClient) {
        LoginUserBO loginUserBO = LoginDTOToBOConvertor.INSTANCE.convert(loginUserDto, sysApp);
        LoginSuccessBO login = AuthHandler.loginDispatch(loginUserBO, sysClient);
        return LoginBOToVOConvertor.INSTANCE.convert(login);
    }

    /**
     * 退出登录
     */
    public void logout() {

    }

    /**
     * 检查登录
     * 1、登录失败，记录失败次数，超出则锁定登录
     * 2、登录成功，失败次数清 0
     */
    public void checkLogin(String tenantId, String username, Supplier<Boolean> checkPasswordResult) {
    }

    public void checkTenant(String tenantId) {
        // 校验租户是否存在
        if (!StringUtils.hasText(tenantId)) {
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
        if (ObjectUtil.isNotNull(sysTenant.getExpireTime())
                && new Date().after(sysTenant.getExpireTime())) {
            log.info("租户 {} 已超过有效期.", tenantId);
            throw new TenantException("租户已超过有效期");
        }
    }
}
