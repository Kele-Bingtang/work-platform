package cn.youngkbt.auth.controller;

import cn.hutool.core.util.StrUtil;
import cn.youngkbt.auth.dto.OpenApiClientDTO;
import cn.youngkbt.auth.entity.SysAuthClient;
import cn.youngkbt.auth.provider.AuthTokenGeneratorProvider;
import cn.youngkbt.auth.service.OpenApiService;
import cn.youngkbt.auth.service.impl.OpenApiServiceImpl;
import cn.youngkbt.auth.vo.OpenApiTokenVO;
import cn.youngkbt.core.error.Assert;
import cn.youngkbt.core.exception.ServiceException;
import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.security.enumeration.AuthErrorCodeEnum;
import cn.youngkbt.security.enumeration.AuthGrantTypeEnum;
import cn.youngkbt.utils.StringUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2023/9/24 2:38
 * @note
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "开放接口", description = "应用认证 Controller")
public class OpenApiController {
    private final OpenApiService openApiService;
    private final AuthTokenGeneratorProvider oauthTokenGeneratorProvider;

    public OpenApiController(OpenApiServiceImpl openApiService, AuthTokenGeneratorProvider oauthTokenGeneratorProvider) {
        this.openApiService = openApiService;
        this.oauthTokenGeneratorProvider = oauthTokenGeneratorProvider;
    }

    @GetMapping("/getToken")
    public Response<OpenApiTokenVO> getToken(@RequestParam("grantType") String grantType, HttpServletRequest request) {
        // 获取认证方式的枚举
        AuthGrantTypeEnum authGrantTypeEnum = AuthGrantTypeEnum.getByGrantType(grantType);
        if (Objects.isNull(authGrantTypeEnum)) {
            throw new ServiceException(AuthErrorCodeEnum.BAD_REQUEST);
        }
        // 获取认证信息：clientId、clientSecret
        String[] authorization = processBasicAuthorization(request);
        SysAuthClient authClient = openApiService.getApp(authorization[0], authorization[1], authGrantTypeEnum.getGrantType());
        // 暂时不支持的认证类型 
        if (authGrantTypeEnum.equals(AuthGrantTypeEnum.PASSWORD) || authGrantTypeEnum.equals(AuthGrantTypeEnum.AUTHORIZATION_CODE) || authGrantTypeEnum.equals(AuthGrantTypeEnum.REFRESH_TOKEN)) {
            throw new ServiceException(AuthErrorCodeEnum.AUTH_APP_GRANT_TYPE_NOT_REALIZE);
        }
        if (authGrantTypeEnum.equals(AuthGrantTypeEnum.CLIENT_CREDENTIALS)) {
            OpenApiTokenVO openApiTokenVO = oauthTokenGeneratorProvider.createTwoToken(authClient.getAppId());
            Assert.nonNull(openApiTokenVO, "访问令牌不能为空");
            return HttpResult.ok(openApiTokenVO);
        }
        throw new IllegalArgumentException("未知授权类型：" + grantType);
    }

    @GetMapping("/refreshToken")
    public Response<OpenApiTokenVO> refreshToken(@RequestParam("grantType") String grantType, @RequestParam("appId") String appId, @RequestParam("refreshToken") String refreshToken) {
        // 获取认证方式的枚举 
        AuthGrantTypeEnum authGrantTypeEnum = AuthGrantTypeEnum.getByGrantType(grantType);
        if (Objects.isNull(authGrantTypeEnum)) {
            throw new ServiceException(AuthErrorCodeEnum.BAD_REQUEST.getCode(), AuthErrorCodeEnum.BAD_REQUEST.getMessage());
        }
        OpenApiTokenVO openApiTokenVO = oauthTokenGeneratorProvider.refreshToken(refreshToken, appId);
        return HttpResult.ok(openApiTokenVO);
    }

    @PostMapping("/add")
    public Response<String> addApp(OpenApiClientDTO openApiClientDTO) {
        boolean isAdd = openApiService.addApp(openApiClientDTO);
        return HttpResult.okOrFail(isAdd);
    }

    private String[] processBasicAuthorization(HttpServletRequest request) {
        String[] clientIdAndSecret = obtainBasicAuthorization(request);
        if (Objects.isNull(clientIdAndSecret) || clientIdAndSecret.length != 2) {
            throw new ServiceException(AuthErrorCodeEnum.BAD_REQUEST.getCode(), "appId 或 appSecret 未正确传递");
        }
        return clientIdAndSecret;
    }

    public static String[] obtainBasicAuthorization(HttpServletRequest request) {
        String clientId;
        String clientSecret; // 先从 Header 中获取
        String authorization = request.getHeader("Authorization");
        authorization = StrUtil.subAfter(authorization, "Basic ", true);
        if (StringUtil.hasText(authorization)) {
            authorization = new String(Base64.getDecoder().decode(authorization.getBytes()));
            clientId = StrUtil.subBefore(authorization, ":", false);
            clientSecret = StrUtil.subAfter(authorization, ":", false);
            // 再从 Param 中获取
        } else {
            clientId = request.getParameter("appId");
            clientSecret = request.getParameter("appSecret");
        }
        // 如果两者非空，则返回 
        if (!StringUtil.hasText(clientId) && StringUtil.hasText(clientSecret)) {
            return new String[]{clientId, clientSecret};
        }
        return null;
    }
}