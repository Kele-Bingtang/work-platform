package cn.youngkbt.uac.auth.controller.system;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysUserDTO;
import cn.youngkbt.uac.sys.model.vo.SysUserVO;
import cn.youngkbt.uac.sys.model.vo.extra.RolePostVo;
import cn.youngkbt.uac.sys.service.SysUserService;
import cn.youngkbt.utils.StringUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023/12/4 19:58
 * @note
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/user")
public class SysUserController {

    private final SysUserService sysUserService;

    @GetMapping("/{id}")
    @Operation(summary = "用户列表查询", description = "通过主键查询用户列表")
    public Response<SysUserVO> listById(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        SysUserVO sysUserVo = sysUserService.listById(id);
        return HttpResult.ok(sysUserVo);
    }

    @GetMapping("/list")
    @Operation(summary = "用户列表查询", description = "通过条件查询用户列表（分页）")
    public Response<List<SysUserVO>> list(SysUserDTO sysUserDto, PageQuery pageQuery) {
        List<SysUserVO> sysUserVOList = sysUserService.listWithPage(sysUserDto, pageQuery);
        return HttpResult.ok(sysUserVOList);
    }

    @GetMapping("/rolePostList")
    @Operation(summary = "角色岗位列表查询", description = "查询角色列表和岗位列表")
    public Response<RolePostVo> rolePostList() {
        RolePostVo rolePostVo = sysUserService.rolePostList();
        return HttpResult.ok(rolePostVo);
    }

    @GetMapping("/listWithDisabledByGroupId/{userGroupId}")
    @Operation(summary = "用户列表查询", description = "下拉查询用户列表（已选的被禁用）")
    public Response<List<SysUserVO>> listWithDisabledByGroupId(@PathVariable String userGroupId) {
        List<SysUserVO> sysUserVOList = sysUserService.listWithDisabledByGroupId(userGroupId);
        return HttpResult.ok(sysUserVOList);
    }

    @PostMapping
    @Operation(summary = "用户列表新增", description = "新增用户列表")
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysUserDTO sysUserDto) {
        if (sysUserService.checkUserNameUnique(sysUserDto)) {
            return HttpResult.failMessage("新增用户「" + sysUserDto.getUsername() + "」失败，登录账号已存在");
        } else if (StringUtil.hasText(sysUserDto.getPhone()) && sysUserService.checkPhoneUnique(sysUserDto)) {
            return HttpResult.failMessage("新增用户「" + sysUserDto.getUsername() + "」失败，手机号码已存在");
        } else if (StringUtil.hasText(sysUserDto.getEmail()) && sysUserService.checkEmailUnique(sysUserDto)) {
            return HttpResult.failMessage("新增用户「" + sysUserDto.getUsername() + "」失败，邮箱账号已存在");
        }

        return HttpResult.ok(sysUserService.insertOne(sysUserDto));
    }

    @PutMapping
    @Operation(summary = "用户列表修改", description = "修改用户列表")
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysUserDTO sysUserDto) {
        if (sysUserService.checkUserNameUnique(sysUserDto)) {
            return HttpResult.failMessage("修改用户「" + sysUserDto.getUsername() + "」失败，登录账号已存在");
        } else if (StringUtil.hasText(sysUserDto.getPhone()) && sysUserService.checkPhoneUnique(sysUserDto)) {
            return HttpResult.failMessage("修改用户「" + sysUserDto.getUsername() + "」失败，手机号码已存在");
        } else if (StringUtil.hasText(sysUserDto.getEmail()) && sysUserService.checkEmailUnique(sysUserDto)) {
            return HttpResult.failMessage("修改用户「" + sysUserDto.getUsername() + "」失败，邮箱账号已存在");
        }

        return HttpResult.ok(sysUserService.updateOne(sysUserDto));
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "用户列表删除", description = "通过主键批量删除用户列表")
    public Response<Boolean> removeBatch(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return HttpResult.ok(sysUserService.removeBatch(List.of(ids)));
    }
}
