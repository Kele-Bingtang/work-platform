package cn.youngkbt.uac.auth.controller;

import cn.youngkbt.core.constants.ColumnConstant;
import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.encrypt.annotation.ApiEncrypt;
import cn.youngkbt.security.domain.LoginUser;
import cn.youngkbt.security.enumeration.AuthErrorCodeEnum;
import cn.youngkbt.security.utils.SecurityUtils;
import cn.youngkbt.tenant.helper.TenantHelper;
import cn.youngkbt.uac.auth.model.dto.LoginUserDTO;
import cn.youngkbt.uac.auth.model.vo.LoginTenantSelectVO;
import cn.youngkbt.uac.auth.model.vo.LoginVO;
import cn.youngkbt.uac.auth.model.vo.TenantSelectVO;
import cn.youngkbt.uac.auth.model.vo.UserInfoVO;
import cn.youngkbt.uac.auth.service.LoginService;
import cn.youngkbt.uac.core.helper.UacHelper;
import cn.youngkbt.uac.system.model.dto.SysTenantDTO;
import cn.youngkbt.uac.system.model.po.SysApp;
import cn.youngkbt.uac.system.model.po.SysClient;
import cn.youngkbt.uac.system.model.vo.SysTenantVO;
import cn.youngkbt.uac.system.service.SysAppService;
import cn.youngkbt.uac.system.service.SysClientService;
import cn.youngkbt.uac.system.service.SysTenantService;
import cn.youngkbt.utils.ListUtil;
import cn.youngkbt.utils.MapstructUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    private final SysTenantService sysTenantService;

    @PostMapping("/login")
    @Operation(summary = "执行登录")
    @ApiEncrypt(request = true, response = false)
    public Response<LoginVO> login(@Valid @RequestBody LoginUserDTO loginUserDTO) {
        String appId = loginUserDTO.getAppId();
        // 校验 App ID
        SysApp sysApp = appService.checkAppIdThenGet(loginUserDTO.getTenantId(), appId);
        if (Objects.isNull(sysApp)) {
            log.info("应用[{}]不存在", appId);
            return HttpResult.failMessage("应用[" + appId + "]不存在");
        }
        String appName = sysApp.getAppName();
        if (!ColumnConstant.STATUS_NORMAL.equals(sysApp.getStatus())) {
            log.info("[{}]已被禁用", appName);
            return HttpResult.failMessage("[" + appName + "]已被禁用");
        }
        // 校验客户端
        SysClient sysClient = clientService.checkClientIdThenGet(sysApp.getClientId());
        String clientName = sysClient.getClientName();
        if (!ColumnConstant.STATUS_NORMAL.equals(sysClient.getStatus())) {
            log.info("客户端[{}]已被禁用.", clientName);
            return HttpResult.failMessage("客户端[" + clientName + "]已被禁用");
        }
        // 校验租户 ID
        loginService.checkTenant(loginUserDTO.getTenantId());
        // 执行登录
        return HttpResult.ok(loginService.login(loginUserDTO, sysApp, sysClient));
    }
    
    @PostMapping("/logout")
    @Operation(summary = "退出登录")
    public Response<String> logout() {
        loginService.logout();
        return HttpResult.ok("退出成功");
    }

    @GetMapping("/tenant/list")
    @Operation(summary = "多租户下拉选项")
    public Response<LoginTenantSelectVO> tenantSelectOption(HttpServletRequest request) {
        List<SysTenantVO> sysTenantVOList = sysTenantService.listAll(new SysTenantDTO());
        List<TenantSelectVO> tenantSelectVOList = MapstructUtil.convert(sysTenantVOList, TenantSelectVO.class);

        // 获取域名
        String serverName = request.getServerName();
        List<TenantSelectVO> list = ListUtil.newArrayList();

        // 本地不需要过滤
        if ("localhost".equals(serverName)) {
            list = tenantSelectVOList;
        } else if (!ListUtil.isEmpty(tenantSelectVOList)) {
            // 过滤出租户所在的域名
            list = tenantSelectVOList.stream().filter(tenantSelectVo -> serverName.equals(tenantSelectVo.getDomain())).toList();
        }

        LoginTenantSelectVO loginTenantSelectVo = LoginTenantSelectVO.builder()
                .tenantEnabled(TenantHelper.isEnable())
                .tenantSelectList(list)
                .build();

        return HttpResult.ok(loginTenantSelectVo);
    }
    
    @GetMapping("/getUserInfo")
    @Operation(summary = "获取用户信息")
    public Response<UserInfoVO> getUserInfo() {
        if ("anonymousUser".equals(SecurityUtils.getUsername())) {
            return HttpResult.failMessage("您没有登录！");
        }
        // 获取登录的用户信息
        LoginUser loginUser = UacHelper.getLoginUser();

        if (Objects.isNull(loginUser)) {
            return HttpResult.fail(AuthErrorCodeEnum.UN_AUTHORIZED);
        }

        UserInfoVO userInfoVo = new UserInfoVO();

        userInfoVo.setUser(loginUser)
                .setRoleCodes(loginUser.getRoleCodes())
                .setPermissions(loginUser.getMenuPermission());
        return HttpResult.ok(userInfoVo);
    }
}
