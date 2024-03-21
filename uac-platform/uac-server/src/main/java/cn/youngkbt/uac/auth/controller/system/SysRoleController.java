package cn.youngkbt.uac.auth.controller.system;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysRoleDTO;
import cn.youngkbt.uac.sys.model.dto.UserRoleLinkDTO;
import cn.youngkbt.uac.sys.model.dto.link.RoleLinkUserGroupDTO;
import cn.youngkbt.uac.sys.model.dto.link.UserGroupLinkRoleDTO;
import cn.youngkbt.uac.sys.model.dto.link.UserLinkRoleDTO;
import cn.youngkbt.uac.sys.model.vo.SysRoleVO;
import cn.youngkbt.uac.sys.model.vo.link.RoleBindUserVO;
import cn.youngkbt.uac.sys.model.vo.link.UserLinkInfoVO;
import cn.youngkbt.uac.sys.model.vo.link.UserRoleListVO;
import cn.youngkbt.uac.sys.service.SysRoleService;
import cn.youngkbt.uac.sys.service.UserGroupRoleLinkService;
import cn.youngkbt.uac.sys.service.UserRoleLinkService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023/12/4 17:39
 * @note
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/role")
public class SysRoleController {

    private final SysRoleService sysRoleService;
    private final UserRoleLinkService userRoleLinkService;
    private final UserGroupRoleLinkService userGroupRoleLinkService;

    @GetMapping("/{id}")
    @Operation(summary = "角色列表查询", description = "通过主键查询角色列表")
    public Response<SysRoleVO> listById(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        SysRoleVO sysRoleVo = sysRoleService.listById(id);
        return HttpResult.ok(sysRoleVo);
    }

    @GetMapping("/list")
    @Operation(summary = "角色列表查询", description = "通过条件查询角色列表（支持分页）")
    public Response<List<SysRoleVO>> list(SysRoleDTO sysRoleDto, PageQuery pageQuery) {
        List<SysRoleVO> sysRoleVOList = sysRoleService.listWithPage(sysRoleDto, pageQuery);
        return HttpResult.ok(sysRoleVOList);
    }

    @PostMapping
    @Operation(summary = "角色列表新增", description = "新增角色")
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysRoleDTO sysRoleDto) {
        if (sysRoleService.checkRoleNameUnique(sysRoleDto)) {
            return HttpResult.failMessage("新增角色「" + sysRoleDto.getRoleName() + "」失败，角色名称已存在");
        } else if (sysRoleService.checkRoleCodeUnique(sysRoleDto)) {
            return HttpResult.failMessage("新增角色「" + sysRoleDto.getRoleName() + "」失败，角色权限已存在");
        }

        return HttpResult.ok(sysRoleService.insertOne(sysRoleDto));
    }

    @PutMapping
    @Operation(summary = "角色列表修改", description = "修改角色")
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysRoleDTO sysRoleDto) {
        if (sysRoleService.checkRoleNameUnique(sysRoleDto)) {
            return HttpResult.failMessage("修改角色「" + sysRoleDto.getRoleName() + "」失败，角色名称已存在");
        } else if (sysRoleService.checkRoleCodeUnique(sysRoleDto)) {
            return HttpResult.failMessage("修改角色「" + sysRoleDto.getRoleName() + "」失败，角色权限已存在");
        }

        return HttpResult.ok(sysRoleService.updateOne(sysRoleDto));
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "角色列表删除", description = "通过主键批量删除角色列表")
    public Response<Boolean> removeBatch(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return HttpResult.ok(sysRoleService.removeBatch(List.of(ids)));
    }

    @GetMapping("/listByUserId/{appId}/{userId}")
    @Operation(summary = "角色列表查询", description = "查询某个用户所在的角色列表")
    public Response<List<UserRoleListVO>> listRoleListByUserId(@PathVariable String appId, @PathVariable String userId) {
        List<UserRoleListVO> userRoleListVOS = sysRoleService.listRoleListByUserId(appId, userId);
        return HttpResult.ok(userRoleListVOS);
    }

    @GetMapping("listWithDisabledByUserId/{appId}/{userId}")
    @Operation(summary = "角色列表查询", description = "查询所有角色列表，如果角色绑定了用户，则 disabled 属性为 false")
    public Response<List<RoleBindUserVO>> listRoleListWithDisabledByUserId(@PathVariable String appId, @PathVariable String userId) {
        List<RoleBindUserVO> roleBindUserVOList = sysRoleService.listRoleListWithDisabledByUserId(appId, userId);
        return HttpResult.ok(roleBindUserVOList);
    }

    @GetMapping("listUserLinkByRoleId/{roleId}")
    @Operation(summary = "用户列表查询", description = "通过角色 ID 查询用户列表")
    public Response<List<UserLinkInfoVO>> listUserLinkByRoleId(@PathVariable String roleId) {
        List<UserLinkInfoVO> userLinkInfoVOList = sysRoleService.listUserLinkByRoleId(roleId);
        return HttpResult.ok(userLinkInfoVOList);
    }

    @GetMapping("/listWithDisabledByGroupId/{userGroupId}")
    @Operation(summary = "角色列表查询", description = "查询角色列表（已选的被禁用）")
    public Response<List<SysRoleVO>> listWithDisabledByGroupId(@PathVariable String userGroupId) {
        List<SysRoleVO> userVOList = sysRoleService.listWithDisabledByGroupId(userGroupId);
        return HttpResult.ok(userVOList);
    }
    
    @PostMapping("addUserToRoles")
    @Operation(summary = "添加用户到角色", description = "添加用户到角色（多个角色）")
    public Response<Boolean> addUserToRoles(@Validated(RestGroup.AddGroup.class) @RequestBody UserLinkRoleDTO userLinkRoleDTO) {
        if (userRoleLinkService.checkUserExistRoles(userLinkRoleDTO.getUserId(), userLinkRoleDTO.getRoleIds())) {
            return HttpResult.failMessage("添加用户到角色失败，用户已存在于角色中");
        }
        boolean result = userRoleLinkService.addUserToRoles(userLinkRoleDTO);
        return HttpResult.ok(result);
    }

    @PostMapping("/addUserGroupsToRole")
    @Operation(summary = "添加角色到用户组", description = "添加角色到用户组（多个用户组）")
    public Response<Boolean> addUserGroupsToRole(@Validated(RestGroup.AddGroup.class) @RequestBody RoleLinkUserGroupDTO roleLinkUserGroupDTO) {
        if (userGroupRoleLinkService.checkRoleExistUserGroups(roleLinkUserGroupDTO.getRoleId(), roleLinkUserGroupDTO.getUserGroupIds())) {
            return HttpResult.failMessage("添加角色到用户组失败，用户组已存在于角色中");
        }
        boolean result = userGroupRoleLinkService.addUserGroupsToRole(roleLinkUserGroupDTO);
        return HttpResult.ok(result);
    }

    @PostMapping("/addUserGroupToRoles")
    @Operation(summary = "添加角色到用户组", description = "添加角色到用户组（多个角色）")
    public Response<Boolean> addUserGroupToRoles(@Validated(RestGroup.AddGroup.class) @RequestBody UserGroupLinkRoleDTO userGroupLinkRoleDTO) {
        if (userGroupRoleLinkService.checkRolesExistUserGroup(userGroupLinkRoleDTO.getRoleIds(), userGroupLinkRoleDTO.getUserGroupId())) {
            return HttpResult.failMessage("添加角色到用户组失败，用户组已存在于角色中");
        }
        boolean result = userGroupRoleLinkService.addUserGroupToRoles(userGroupLinkRoleDTO);
        return HttpResult.ok(result);
    }

    @DeleteMapping("/removeUserFromRole/{ids}")
    @Operation(summary = "移出用户组", description = "将用户移出角色")
    public Response<Boolean> removeUserFromRole(@PathVariable Long[] ids) {
        boolean result = userRoleLinkService.removeUserFromRole(List.of(ids));
        return HttpResult.ok(result);
    }
    
    @DeleteMapping("/removeUserGroupFromRole/{ids}")
    @Operation(summary = "移出角色", description = "将用户组移出角色")
    public Response<Boolean> removeUserGroupFromRole(@PathVariable Long[] ids) {
        boolean result = userGroupRoleLinkService.removeUserGroupFromRole(List.of(ids));
        return HttpResult.ok(result);
    }

    @PutMapping("/updateLinkInfo")
    @Operation(summary = "用户关联角色信息修改", description = "修改用户组和角色关联信息")
    public Response<Boolean> updateLinkInfo(@Validated(RestGroup.EditGroup.class) @RequestBody UserRoleLinkDTO userRoleLinkDTO) {
        return HttpResult.ok(userRoleLinkService.updateOne(userRoleLinkDTO));
    }
}
