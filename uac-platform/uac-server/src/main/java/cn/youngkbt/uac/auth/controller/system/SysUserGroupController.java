package cn.youngkbt.uac.auth.controller.system;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.uac.sys.model.dto.SysUserGroupDTO;
import cn.youngkbt.uac.sys.model.dto.UserGroupLinkDTO;
import cn.youngkbt.uac.sys.model.dto.link.UserGroupLinkInfoDTO;
import cn.youngkbt.uac.sys.model.dto.link.UserGroupLinkRoleDTO;
import cn.youngkbt.uac.sys.model.dto.link.UserGroupLinkUserDTO;
import cn.youngkbt.uac.sys.model.vo.SysUserGroupVO;
import cn.youngkbt.uac.sys.model.vo.link.UserGroupBindSelectVO;
import cn.youngkbt.uac.sys.model.vo.link.UserGroupLinkVO;
import cn.youngkbt.uac.sys.service.SysUserGroupService;
import cn.youngkbt.uac.sys.service.UserGroupLinkService;
import cn.youngkbt.uac.sys.service.UserGroupRoleLinkService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024/3/12 23:59
 * @note
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/userGroup")
public class SysUserGroupController {

    private final SysUserGroupService sysUserGroupService;
    private final UserGroupLinkService userGroupLinkService;
    private final UserGroupRoleLinkService userGroupRoleLinkService;

    @GetMapping("/list")
    @Operation(summary = "用户组列表查询", description = "通过主键查询用户组列表")
    @PreAuthorize("hasAuthority('system:userGroup:list')")
    public Response<List<SysUserGroupVO>> list(SysUserGroupDTO sysUserGroupDTO) {
        List<SysUserGroupVO> sysUserGroupVOList = sysUserGroupService.queryList(sysUserGroupDTO);
        return HttpResult.ok(sysUserGroupVOList);
    }

    @GetMapping("/listPage")
    @Operation(summary = "用户组列表查询", description = "通过主键查询用户组列表（分页）")
    @PreAuthorize("hasAuthority('system:userGroup:list')")
    public Response<TablePage<SysUserGroupVO>> listPage(SysUserGroupDTO sysUserGroupDTO, PageQuery pageQuery) {
        TablePage<SysUserGroupVO> tablePage = sysUserGroupService.listPage(sysUserGroupDTO, pageQuery);
        return HttpResult.ok(tablePage);
    }

    @GetMapping("/listUserGroupByUserId/{appId}/{userId}")
    @Operation(summary = "用户组列表查询", description = "查询某个用户所在的用户组列表")
    @PreAuthorize("hasAuthority('system:userGroup:query')")
    public Response<List<UserGroupLinkVO>> listUserGroupByUserId(@PathVariable String appId, @PathVariable String userId) {
        List<UserGroupLinkVO> tablePage = userGroupLinkService.listUserGroupByUserId(appId, userId);
        return HttpResult.ok(tablePage);
    }

    @GetMapping("/listUserGroupByRoleId/{roleId}")
    @Operation(summary = "用户组列表查询", description = "查询某个角色绑定的用户组列表")
    @PreAuthorize("hasAuthority('system:userGroup:query')")
    public Response<TablePage<UserGroupLinkVO>> listUserGroupByRoleId(@PathVariable String roleId, UserGroupLinkInfoDTO userGroupLinkInfoDTO, PageQuery pageQuery) {
        TablePage<UserGroupLinkVO> tablePage = userGroupRoleLinkService.listUserGroupByRoleId(roleId, userGroupLinkInfoDTO, pageQuery);
        return HttpResult.ok(tablePage);
    }

    @GetMapping("listWithDisabledByUserId/{appId}/{userId}")
    @Operation(summary = "用户组列表查询", description = "查询所有用户组列表，如果用户组存在用户，则 disabled 属性为 true")
    @PreAuthorize("hasAuthority('system:userGroup:query')")
    public Response<List<UserGroupBindSelectVO>> listWithDisabledByUserId(@PathVariable String appId, @PathVariable String userId) {
        List<UserGroupBindSelectVO> sysUserGroupVOList = userGroupLinkService.listWithDisabledByUserId(appId, userId);
        return HttpResult.ok(sysUserGroupVOList);
    }

    @GetMapping("listWithDisabledByRoleId/{roleId}")
    @Operation(summary = "用户组列表查询", description = "查询所有用户组列表，如果用户组绑定角色，则 disabled 属性为 true")
    @PreAuthorize("hasAuthority('system:userGroup:query')")
    public Response<List<UserGroupBindSelectVO>> listWithDisabledByRoleId(@PathVariable String roleId) {
        List<UserGroupBindSelectVO> sysUserGroupVOList = userGroupRoleLinkService.listWithDisabledByRoleId(roleId);
        return HttpResult.ok(sysUserGroupVOList);
    }

    @PostMapping("/addRolesToUserGroup")
    @Operation(summary = "添加角色到用户组", description = "添加角色到用户组（多个角色）")
    @PreAuthorize("hasAuthority('system:userGroup:add')")
    public Response<Boolean> addRolesToUserGroup(@Validated(RestGroup.AddGroup.class) @RequestBody UserGroupLinkRoleDTO userGroupLinkRoleDTO) {
        if (userGroupRoleLinkService.checkRolesExistUserGroup(userGroupLinkRoleDTO)) {
            return HttpResult.failMessage("添加角色到用户组失败，用户组已存在于角色中");
        }
        boolean result = userGroupRoleLinkService.addRolesToUserGroup(userGroupLinkRoleDTO);
        return HttpResult.ok(result);
    }

    @PostMapping("/addUsersToGroup")
    @Operation(summary = "添加用户到用户组", description = "添加用户到用户组（多个用户）")
    @PreAuthorize("hasAuthority('system:userGroup:add')")
    public Response<Boolean> addUsersToUserGroup(@Validated(RestGroup.AddGroup.class) @RequestBody UserGroupLinkUserDTO userGroupLinkUserDTO) {
        if (userGroupLinkService.checkUsersExistUserGroup(userGroupLinkUserDTO)) {
            return HttpResult.failMessage("添加用户到用户组失败，用户已存在于用户组中");
        }
        boolean result = userGroupLinkService.addUsersToUserGroup(userGroupLinkUserDTO);
        return HttpResult.ok(result);
    }

    @DeleteMapping("/removeUserFromUserGroup/{ids}")
    @Operation(summary = "移出用户组", description = "将用户移出项目组")
    @PreAuthorize("hasAuthority('system:userGroup:remove')")
    public Response<Boolean> removeUserFromUserGroup(@PathVariable Long[] ids) {
        boolean result = userGroupLinkService.removeUserFromUserGroup(List.of(ids));
        return HttpResult.ok(result);
    }

    @PutMapping("/updateLinkInfo")
    @Operation(summary = "用户关联用户信息修改", description = "修改用户组和用户䣌关联信息")
    @PreAuthorize("hasAuthority('system:userGroup:edit')")
    public Response<Boolean> updateLinkInfo(@Validated(RestGroup.EditGroup.class) @RequestBody UserGroupLinkDTO userGroupLinkDTO) {
        return HttpResult.ok(userGroupLinkService.updateOne(userGroupLinkDTO));
    }

    @PostMapping
    @Operation(summary = "用户组列表新增", description = "新增用户组")
    @PreAuthorize("hasAuthority('system:userGroup:add')")
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysUserGroupDTO sysUserGroupDTO) {
        if (sysUserGroupService.checkUserGroupNameUnique(sysUserGroupDTO)) {
            return HttpResult.failMessage("新增用户组「" + sysUserGroupDTO.getGroupName() + "」失败，用户组名称已存在");
        }

        return HttpResult.ok(sysUserGroupService.insertOne(sysUserGroupDTO));
    }

    @PutMapping
    @Operation(summary = "用户组列表修改", description = "修改用户组")
    @PreAuthorize("hasAuthority('system:userGroup:edit')")
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysUserGroupDTO sysUserGroupDTO) {
        if (sysUserGroupService.checkUserGroupNameUnique(sysUserGroupDTO)) {
            return HttpResult.failMessage("修改用户组「" + sysUserGroupDTO.getGroupName() + "」失败，用户组名称已存在");
        }
        return HttpResult.ok(sysUserGroupService.updateOne(sysUserGroupDTO));
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "用户组列表删除", description = "通过主键批量删除用户组列表")
    @PreAuthorize("hasAuthority('system:userGroup:remove')")
    public Response<Boolean> removeBatch(@PathVariable Long[] ids, @RequestBody List<String> userGroupIds) {
        return HttpResult.ok(sysUserGroupService.removeBatch(List.of(ids), userGroupIds));
    }
}
