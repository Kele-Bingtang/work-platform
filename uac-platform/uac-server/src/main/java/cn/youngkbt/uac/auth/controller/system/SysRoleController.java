package cn.youngkbt.uac.auth.controller.system;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysRoleDTO;
import cn.youngkbt.uac.sys.model.vo.SysRoleVO;
import cn.youngkbt.uac.sys.model.vo.extra.RoleBindUserVO;
import cn.youngkbt.uac.sys.service.SysRoleService;
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
    public Response<List<SysRoleVO>> listRoleListByUserId(@PathVariable String appId, @PathVariable String userId) {
        List<SysRoleVO> sysRoleVOList = sysRoleService.listRoleListByUserId(appId, userId);
        return HttpResult.ok(sysRoleVOList);
    }

    @GetMapping("listWithDisabledByUserId/{appId}/{userId}")
    @Operation(summary = "角色列表查询", description = "查询所有角色列表，如果角色绑定了用户，则 disabled 属性为 false")
    public Response<List<RoleBindUserVO>> listRoleListWithDisabledByUserId(@PathVariable String appId, @PathVariable String userId) {
        List<RoleBindUserVO> roleBindUserVOList = sysRoleService.listRoleListWithDisabledByUserId(appId, userId);
        return HttpResult.ok(roleBindUserVOList);
    }

    /**
     * 绑定用户
     */
    // @PostMapping("addUserToRoles")
    // public Response<Boolean> addUserToRoles() {
    // }
}
