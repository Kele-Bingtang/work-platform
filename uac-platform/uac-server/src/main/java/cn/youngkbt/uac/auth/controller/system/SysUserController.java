package cn.youngkbt.uac.auth.controller.system;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysUserDTO;
import cn.youngkbt.uac.sys.model.dto.link.UserLinkInfoDTO;
import cn.youngkbt.uac.sys.model.vo.SysUserVO;
import cn.youngkbt.uac.sys.model.vo.extra.RolePostVo;
import cn.youngkbt.uac.sys.model.vo.link.UserBindSelectVO;
import cn.youngkbt.uac.sys.model.vo.link.UserLinkVO;
import cn.youngkbt.uac.sys.service.SysUserService;
import cn.youngkbt.uac.sys.service.UserGroupLinkService;
import cn.youngkbt.uac.sys.service.UserRoleLinkService;
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
    private final UserGroupLinkService userGroupLinkService;
    private final UserRoleLinkService userRoleLinkService;

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

    @GetMapping("listUserLinkByRoleId/{roleId}")
    @Operation(summary = "用户列表查询", description = "通过角色 ID 查询用户列表")
    public Response<List<UserLinkVO>> listUserLinkByRoleId(@PathVariable String roleId) {
        List<UserLinkVO> userLinkVOList = userRoleLinkService.listUserLinkByRoleId(roleId);
        return HttpResult.ok(userLinkVOList);
    }

    @GetMapping("listUserLinkByGroupId/{userGroupId}")
    @Operation(summary = "用户列表查询", description = "通过用户组 ID 查询用户列表")
    public Response<List<UserLinkVO>> listUserLinkByGroupId(@PathVariable String userGroupId, UserLinkInfoDTO userLinkInfoDTO) {
        List<UserLinkVO> userLinkVOList = userGroupLinkService.listUserLinkByGroupId(userGroupId, userLinkInfoDTO);
        return HttpResult.ok(userLinkVOList);
    }

    @GetMapping("/listWithDisabledByGroupId/{userGroupId}")
    @Operation(summary = "用户列表查询", description = "下拉查询用户列表，如果用户绑定了用户组，则 disabled 属性为 true")
    public Response<List<UserBindSelectVO>> listWithDisabledByGroupId(@PathVariable String userGroupId) {
        List<UserBindSelectVO> userBindSelectVOList = userGroupLinkService.listWithDisabledByGroupId(userGroupId);
        return HttpResult.ok(userBindSelectVOList);
    }

    @GetMapping("/listWithDisabledByRoleId/{roleId}")
    @Operation(summary = "用户列表查询", description = "下拉查询用户列表，如果用户绑定了角色，则 disabled 属性为 true")
    public Response<List<UserBindSelectVO>> listWithDisabledByRoleId(@PathVariable String roleId) {
        List<UserBindSelectVO> userBindSelectVOList = userRoleLinkService.listWithDisabledByRoleId(roleId);
        return HttpResult.ok(userBindSelectVOList);
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
