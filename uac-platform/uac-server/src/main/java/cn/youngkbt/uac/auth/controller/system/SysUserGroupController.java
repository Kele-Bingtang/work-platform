package cn.youngkbt.uac.auth.controller.system;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysUserGroupDTO;
import cn.youngkbt.uac.sys.model.dto.link.UserLinkUserGroupDTO;
import cn.youngkbt.uac.sys.model.vo.SysUserGroupVO;
import cn.youngkbt.uac.sys.model.vo.extra.UserGroupBindUserVO;
import cn.youngkbt.uac.sys.service.SysUserGroupService;
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

    @GetMapping("/list")
    @Operation(summary = "用户组列表查询", description = "通过主键查询用户组列表")
    public Response<List<SysUserGroupVO>> list(SysUserGroupDTO sysUserGroupDTO, PageQuery pageQuery) {
        List<SysUserGroupVO> sysUserGroupVOList = sysUserGroupService.list(sysUserGroupDTO, pageQuery);
        return HttpResult.ok(sysUserGroupVOList);
    }

    @GetMapping("/listByUserId/{appId}/{userId}")
    @Operation(summary = "用户组列表查询", description = "查询某个用户所在的用户组列表")
    public Response<List<SysUserGroupVO>> listUserIdGroup(@PathVariable String appId, @PathVariable String userId) {
        List<SysUserGroupVO> sysUserGroupVOList = sysUserGroupService.listUserGroupByUserId(appId, userId);
        return HttpResult.ok(sysUserGroupVOList);
    }

    @GetMapping("listWithDisabledByUserId/{appId}/{userId}")
    @Operation(summary = "用户组列表查询", description = "查询所有用户组列表，如果用户组存在用户，则 disabled 属性为 false")
    public Response<List<UserGroupBindUserVO>> listUserGroupWithDisabledByUserId(@PathVariable String appId, @PathVariable String userId) {
        List<UserGroupBindUserVO> sysUserGroupVOList = sysUserGroupService.listUserGroupWithDisabledByUserId(appId, userId);
        return HttpResult.ok(sysUserGroupVOList);
    }
    
    @PostMapping("/addUserToGroups")
    @Operation(summary = "添加用户到用户组", description = "添加用户到用户组（多个用户组）")
    public Response<Boolean> addUserToGroups(@Validated(RestGroup.AddGroup.class) @RequestBody UserLinkUserGroupDTO userLinkUserGroupDTO) {
        boolean result = sysUserGroupService.addUserToGroups(userLinkUserGroupDTO);
        return HttpResult.ok(result);
    }

}
