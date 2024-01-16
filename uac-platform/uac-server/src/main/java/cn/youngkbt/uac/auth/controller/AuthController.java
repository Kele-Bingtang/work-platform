package cn.youngkbt.uac.auth.controller;

import cn.youngkbt.core.constants.ColumnConstant;
import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.security.utils.SecurityUtils;
import cn.youngkbt.tenant.helper.TenantHelper;
import cn.youngkbt.uac.auth.model.dto.LoginUserDto;
import cn.youngkbt.uac.auth.model.vo.LoginTenantSelectVo;
import cn.youngkbt.uac.auth.model.vo.LoginVo;
import cn.youngkbt.uac.auth.model.vo.TenantSelectVo;
import cn.youngkbt.uac.auth.model.vo.UserInfoVo;
import cn.youngkbt.uac.auth.service.LoginService;
import cn.youngkbt.uac.sys.model.dto.SysTenantDto;
import cn.youngkbt.uac.sys.model.po.SysApp;
import cn.youngkbt.uac.sys.model.po.SysClient;
import cn.youngkbt.uac.sys.model.vo.SysTenantVo;
import cn.youngkbt.uac.sys.service.SysAppService;
import cn.youngkbt.uac.sys.service.SysClientService;
import cn.youngkbt.uac.sys.service.SysTenantService;
import cn.youngkbt.utils.ListUtil;
import cn.youngkbt.utils.MapstructUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
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

    /**
     * 执行登录
     */
    @PostMapping("/login")
    public Response<LoginVo> login(@Valid @RequestBody LoginUserDto loginUserDto) {
        // 避免重复登录
        // String username = SecurityUtils.getUsername();
        // if (Objects.nonNull(username) && StringUtils.equals(username, loginUserDTO.getUsername())) {
        //     return HttpResult.ok("用户名已登录");
        // }

        String appId = loginUserDto.getAppId();
        String grantType = loginUserDto.getGrantType();
        // 校验 App ID
        SysApp sysApp = appService.checkAppIdThenGet(appId);
        if (Objects.isNull(sysApp)) {
            log.info("应用[{}]不存在", appId);
            return HttpResult.failMessage("应用[" + appId + "]不存在");
        }
        String appName = sysApp.getAppName();
        if (!ColumnConstant.STATUS_NORMAL.equals(sysApp.getStatus())) {
            log.info("[{}]已被禁用", appName);
            return HttpResult.failMessage("[" + appName + "]已被禁用");
        }
        if (!StringUtils.contains(sysApp.getGrantTypes(), grantType)) {
            log.info("{} 认证类型： {} 异常", appName, grantType);
            return HttpResult.failMessage("[" + appName + "]认证类型不支持");
        }
        // 校验客户端
        SysClient sysClient = clientService.checkClientIdThenGet(sysApp.getClientId());
        String clientName = sysClient.getClientName();
        if (!ColumnConstant.STATUS_NORMAL.equals(sysClient.getStatus())) {
            log.info("客户端[{}]已被禁用.", clientName);
            return HttpResult.failMessage("客户端[" + clientName + "]已被禁用");
        }
        // 校验租户 ID
        loginService.checkTenant(loginUserDto.getTenantId());
        // 执行登录
        return HttpResult.ok(loginService.login(loginUserDto, sysApp, sysClient));
    }

    /**
     * 多租户下拉选项
     */
    @GetMapping("/tenant/list")
    public Response<LoginTenantSelectVo> tenantSelectOption(HttpServletRequest request) {
        List<SysTenantVo> sysTenantVoList = sysTenantService.queryListWithPage(new SysTenantDto(), null);
        List<TenantSelectVo> tenantSelectVoList = MapstructUtil.convert(sysTenantVoList, TenantSelectVo.class);

        // 获取域名
        String serverName = request.getServerName();
        List<TenantSelectVo> list = ListUtil.newArrayList();

        // 本地不需要过滤
        if ("localhost".equals(serverName)) {
            list = tenantSelectVoList;
        } else if (!ListUtil.isEmpty(tenantSelectVoList)) {
            // 过滤出租户所在的域名
            list = tenantSelectVoList.stream().filter(tenantSelectVo -> serverName.equals(tenantSelectVo.getDomain())).toList();
        }

        LoginTenantSelectVo loginTenantSelectVo = LoginTenantSelectVo.builder()
                .tenantEnabled(TenantHelper.isEnable())
                .tenantSelectList(list)
                .build();

        return HttpResult.ok(loginTenantSelectVo);
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/getUserInfo")
    public Response<UserInfoVo> getUserInfo() {
        // 获取登录的用户信息
        Authentication authentication = SecurityUtils.getAuthentication();
        if("anonymousUser".equals(authentication.getPrincipal())) {
            return HttpResult.failMessage("您没有登录！");
        }
        return null;
    }

    @GetMapping("/test")
    public Response<String> testLogin() {
        return HttpResult.ok("登录成功");
    }
}
