package cn.youngkbt.uac.auth.controller;

import cn.youngkbt.core.constants.ColumnConstant;
import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.uac.auth.dto.LoginUserDTO;
import cn.youngkbt.uac.auth.service.LoginService;
import cn.youngkbt.uac.auth.vo.LoginVO;
import cn.youngkbt.uac.sys.model.po.SysApp;
import cn.youngkbt.uac.sys.model.po.SysClient;
import cn.youngkbt.uac.sys.service.SysAppService;
import cn.youngkbt.uac.sys.service.SysClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2023/11/12 14:14
 * @note 认证 Controller
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final SysAppService appService;
    private final SysClientService clientService;
    private final LoginService loginService;

    /**
     * 执行登录
     */
    @PostMapping("/login")
    public Response<LoginVO> login(@Valid @RequestBody LoginUserDTO loginUserDTO) {
        String appId = loginUserDTO.getAppId();
        String grantType = loginUserDTO.getGrantType();
        // 校验 App ID
        SysApp sysApp = appService.checkAppIdThenGet(appId);
        if (Objects.isNull(sysApp)) {
            log.info("应用 {} 不存在", appId);
            return HttpResult.fail("应用 " + appId + " 不存在");
        }
        String appName = sysApp.getAppName();
        if (!ColumnConstant.STATUS_NORMAL.equals(sysApp.getStatus())) {
            log.info("应用 {} 已被禁用", appName);
            return HttpResult.fail("应用 " + appName + " 已被禁用");
        }
        if (StringUtils.contains(sysApp.getGrantTypes(), grantType)) {
            log.info("应用 {} 认证类型： {} 异常", appName, grantType);
            return HttpResult.fail("应用 " + appName + " 认证类型不支持");
        }
        // 校验客户端
        SysClient sysClient = clientService.checkClientIdThenGet(sysApp.getClientId());
        String clientName = sysClient.getClientName();
        if (!ColumnConstant.STATUS_NORMAL.equals(sysClient.getStatus())) {
            log.info("客户端 {} 已被禁用.", clientName);
            return HttpResult.fail("客户端 " + clientName + " 已被禁用");
        }
        // 校验租户 ID
        loginService.checkTenant(loginUserDTO.getTenantId());
        // 执行登录
        return HttpResult.ok(loginService.login(loginUserDTO, sysApp, sysClient));
    }

}
