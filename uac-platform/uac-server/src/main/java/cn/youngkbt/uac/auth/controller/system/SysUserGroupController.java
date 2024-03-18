package cn.youngkbt.uac.auth.controller.system;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysUserGroupDTO;
import cn.youngkbt.uac.sys.model.dto.link.UserGroupLinkUserDTO;
import cn.youngkbt.uac.sys.model.dto.link.UserLinkUserGroupDTO;
import cn.youngkbt.uac.sys.model.vo.SysUserGroupVO;
import cn.youngkbt.uac.sys.model.vo.link.UserGroupBindUserVO;
import cn.youngkbt.uac.sys.model.vo.link.UserGroupListVO;
import cn.youngkbt.uac.sys.service.SysUserGroupService;
import cn.youngkbt.uac.sys.service.UserGroupLinkService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/list")
    @Operation(summary = "用户组列表查询", description = "通过主键查询用户组列表")
    public Response<List<SysUserGroupVO>> list(SysUserGroupDTO sysUserGroupDTO, PageQuery pageQuery) {
        List<SysUserGroupVO> sysUserGroupVOList = sysUserGroupService.list(sysUserGroupDTO, pageQuery);
        return HttpResult.ok(sysUserGroupVOList);
    }

    @GetMapping("/listByUserId/{appId}/{userId}")
    @Operation(summary = "用户组列表查询", description = "查询某个用户所在的用户组列表")
    public Response<List<UserGroupListVO>> listUserIdGroup(@PathVariable String appId, @PathVariable String userId) {
        List<UserGroupListVO> userGroupListVOList = sysUserGroupService.listUserGroupByUserId(appId, userId);
        return HttpResult.ok(userGroupListVOList);
    }

    @GetMapping("listWithDisabledByUserId/{appId}/{userId}")
    @Operation(summary = "用户组列表查询", description = "查询所有用户组列表，如果用户组存在用户，则 disabled 属性为 false")
    public Response<List<UserGroupBindUserVO>> listUserGroupWithDisabledByUserId(@PathVariable String appId, @PathVariable String userId) {
        List<UserGroupBindUserVO> sysUserGroupVOList = sysUserGroupService.listUserGroupWithDisabledByUserId(appId, userId);
        return HttpResult.ok(sysUserGroupVOList);
    }

    @PostMapping("/addUserToGroups")
    @Operation(summary = "添加用户到用户组", description = "添加用户到用户组（多个用户组）")
    public Response<Boolean> addUserToUserGroups(@Validated(RestGroup.AddGroup.class) @RequestBody UserLinkUserGroupDTO userLinkUserGroupDTO) {
        if (userGroupLinkService.checkUserExistUserGroups(userLinkUserGroupDTO.getUserId(), userLinkUserGroupDTO.getUserGroupIds())) {
            return HttpResult.failMessage("添加用户到用户组失败，用户已存在于用户组中");
        }
        boolean result = userGroupLinkService.addUserToUserGroups(userLinkUserGroupDTO);
        return HttpResult.ok(result);
    }

    @PostMapping("/addUsersToGroup")
    @Operation(summary = "添加用户到用户组", description = "添加用户到用户组（多个用户）")
    public Response<Boolean> addUsersToUserGroup(@Validated(RestGroup.AddGroup.class) @RequestBody UserGroupLinkUserDTO userGroupLinkUserDTO) {
        if (userGroupLinkService.checkUsersExistUserGroup(userGroupLinkUserDTO.getUserIds(), userGroupLinkUserDTO.getUserGroupId())) {
            return HttpResult.failMessage("添加用户到用户组失败，用户已存在于用户组中");
        }
        boolean result = userGroupLinkService.addUsersToUserGroup(userGroupLinkUserDTO);
        return HttpResult.ok(result);
    }

    @DeleteMapping("/removeUserFromUserGroup/{userId}/{userGroupId}")
    @Operation(summary = "移出用户组", description = "将用户移出项目组")
    public Response<Boolean> removeUserFromUserGroup(@PathVariable String userId, @PathVariable String userGroupId) {
        boolean result = userGroupLinkService.removeUserFromUserGroup(userId, userGroupId);
        return HttpResult.ok(result);
    }

    @PostMapping
    @Operation(summary = "用户组列表新增", description = "新增用户组")
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysUserGroupDTO sysUserGroupDto) {
        if (sysUserGroupService.checkUserGroupNameUnique(sysUserGroupDto)) {
            return HttpResult.failMessage("修改用户组「" + sysUserGroupDto.getGroupName() + "」失败，用户组名称已存在");
        }

        return HttpResult.ok(sysUserGroupService.insertOne(sysUserGroupDto));
    }

    @PutMapping
    @Operation(summary = "用户组列表修改", description = "修改用户组")
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysUserGroupDTO sysUserGroupDto) {
        if (sysUserGroupService.checkUserGroupNameUnique(sysUserGroupDto)) {
            return HttpResult.failMessage("修改用户组「" + sysUserGroupDto.getGroupName() + "」失败，用户组名称已存在");
        }
        return HttpResult.ok(sysUserGroupService.updateOne(sysUserGroupDto));
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "用户组列表删除", description = "通过主键批量删除用户组列表")
    public Response<Boolean> removeBatch(@PathVariable Long[] ids) {
        return HttpResult.ok(sysUserGroupService.removeBatch(List.of(ids)));
    }
}
