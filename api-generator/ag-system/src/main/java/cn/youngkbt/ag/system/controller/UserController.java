package cn.youngkbt.ag.system.controller;

import cn.youngkbt.ag.system.model.dto.UserDTO;
import cn.youngkbt.ag.system.model.vo.UserVO;
import cn.youngkbt.ag.system.service.UserService;
import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024/7/28 18:39:22
 * @note
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/listByKeyword/{keyword}")
    @Operation(summary = "通过关键字查询用户", description = "通过关键字查询用户")
    public Response<List<UserVO>> listByKeyword(@PathVariable String keyword) {
        List<UserVO> userVOList = userService.listByKeyword(keyword);
        return HttpResult.ok(userVOList);
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "用户注册")
    public Response<Boolean> register(@Validated(RestGroup.AddGroup.class) @RequestBody UserDTO userDTO) {
        if (userService.checkUsernameUnique(userDTO)) {
            return HttpResult.failMessage("用户名已存在");
        }

        return HttpResult.ok(userService.register(userDTO));
    }

    @PutMapping
    @Operation(summary = "用户修改", description = "用户修改")
    public Response<Boolean> edit(@Validated(RestGroup.EditGroup.class)  @RequestBody UserDTO userDTO) {
        return HttpResult.ok(userService.edit(userDTO));
    }
}
