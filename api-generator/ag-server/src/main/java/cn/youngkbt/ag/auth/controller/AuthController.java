package cn.youngkbt.ag.auth.controller;

import cn.youngkbt.ag.auth.model.dto.LoginUserDTO;
import cn.youngkbt.ag.auth.model.vo.LoginVO;
import cn.youngkbt.ag.auth.service.LoginService;
import cn.youngkbt.ag.core.helper.AgHelper;
import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.encrypt.annotation.ApiEncrypt;
import cn.youngkbt.security.domain.LoginUser;
import cn.youngkbt.security.enumeration.AuthErrorCodeEnum;
import cn.youngkbt.security.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2024/6/22 16:10:46
 * @note
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final LoginService loginService;
    
    @PostMapping("/login")
    @Operation(summary = "执行登录")
    @ApiEncrypt(request = true, response = false)
    public Response<LoginVO> login(@Valid @RequestBody LoginUserDTO loginUserDTO) {
        return HttpResult.ok(loginService.login(loginUserDTO));
    }

    @PostMapping("/logout")
    @Operation(summary = "退出登录")
    public Response<String> logout() {
        loginService.logout();
        return HttpResult.ok("退出成功");
    }

    @GetMapping("/getUserInfo")
    @Operation(summary = "获取用户信息")
    public Response<LoginUser> getUserInfo() {
        if ("anonymousUser".equals(SecurityUtils.getUsername())) {
            return HttpResult.failMessage("您没有登录！");
        }
        // 获取登录的用户信息
        LoginUser loginUser = AgHelper.getLoginUser();

        if (Objects.isNull(loginUser)) {
            return HttpResult.fail(AuthErrorCodeEnum.UN_AUTHORIZED);
        }
        return HttpResult.ok(loginUser);
    }
}
