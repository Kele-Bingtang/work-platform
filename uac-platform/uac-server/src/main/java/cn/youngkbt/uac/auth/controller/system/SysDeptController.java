package cn.youngkbt.uac.auth.controller.system;

import cn.hutool.core.lang.tree.Tree;
import cn.youngkbt.core.constants.ColumnConstant;
import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.uac.sys.model.dto.SysDeptDTO;
import cn.youngkbt.uac.sys.model.vo.SysDeptVO;
import cn.youngkbt.uac.sys.service.SysDeptService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023/12/2 23:22
 * @note 部门 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dept")
public class SysDeptController {

    private final SysDeptService sysDeptService;

    @GetMapping("/list")
    @Operation(summary = "部门列表查询", description = "通过部门条件查询部门列表")
    @PreAuthorize("hasAuthority('system:dept:list')")
    public Response<List<SysDeptVO>> list(SysDeptDTO sysDeptDTO) {
        List<SysDeptVO> sysDeptVOList = sysDeptService.queryList(sysDeptDTO);
        return HttpResult.ok(sysDeptVOList);
    }

    @GetMapping("/listPage")
    @Operation(summary = "部门列表查询", description = "通过部门条件查询部门列表（支持分页）")
    @PreAuthorize("hasAuthority('system:dept:list')")
    public Response<TablePage<SysDeptVO>> listPage(SysDeptDTO sysDeptDTO, PageQuery pageQuery) {
        TablePage<SysDeptVO> tablePage = sysDeptService.listPage(sysDeptDTO, pageQuery);
        return HttpResult.ok(tablePage);
    }

    @GetMapping("/treeList")
    @Operation(summary = "部门树形结构查询", description = "通过部门条件查询部门树形结构")
    @PreAuthorize("hasAuthority('system:dept:query')")
    public Response<List<Tree<String>>> listDeptTreeList(SysDeptDTO sysDeptDTO) {
        List<Tree<String>> deptTreeList = sysDeptService.listDeptTreeList(sysDeptDTO);
        return HttpResult.ok(deptTreeList);
    }

    @GetMapping("/treeTable")
    @Operation(summary = "部门树形表格查询", description = "通过部门条件查询部门树形表格")
    @PreAuthorize("hasAuthority('system:dept:list')")
    public Response<List<SysDeptVO>> listDeptTreeTable(SysDeptDTO sysDeptDTO) {
        List<SysDeptVO> treeTable = sysDeptService.listDeptTreeTable(sysDeptDTO);
        return HttpResult.ok(treeTable);
    }

    @GetMapping("/listDeptListByRoleId/{appId}/{roleId}")
    @Operation(summary = "菜单列表查询", description = "通过角色 ID 查询菜单 ID 列表")
    @PreAuthorize("hasAuthority('system:dept:query')")
    public Response<List<Tree<String>>> listDeptListByRoleId(@PathVariable String appId, @PathVariable String roleId) {
        List<Tree<String>> sysMenuVOList = sysDeptService.listDeptListByRoleId(roleId, appId);
        return HttpResult.ok(sysMenuVOList);
    }

    @GetMapping("/parentDept")
    @Operation(summary = "部门父级部门查询", description = "通过部门 ID 查询父级部门")
    @PreAuthorize("hasAuthority('system:dept:query')")
    public Response<SysDeptVO> listParentDeptByDeptId(String deptId) {
        SysDeptVO sysDeptVo = sysDeptService.listParentDeptByDeptId(deptId);
        return HttpResult.ok(sysDeptVo);
    }

    @GetMapping("/deptNames")
    @Operation(summary = "部门名称查询", description = "通过部门 ID 列表查询部门名称")
    @PreAuthorize("hasAuthority('system:dept:query')")
    public Response<List<String>> listDeptNamesByIds(List<String> ids) {
        List<String> deptNameList = sysDeptService.listDeptNamesByIds(ids);
        return HttpResult.ok(deptNameList);
    }

    @GetMapping("/userCount")
    @Operation(summary = "部门用户数量查询", description = "通过部门 ID 查询部门用户数量")
    @PreAuthorize("hasAuthority('system:dept:query')")
    public Response<Integer> getDeptUserCount(String deptId) {
        Integer deptUserCount = sysDeptService.getDeptUserCount(deptId);
        return HttpResult.ok(deptUserCount);
    }

    @PostMapping
    @Operation(summary = "部门新增", description = "新增部门")
    @PreAuthorize("hasAuthority('system:dept:add')")
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysDeptDTO sysDeptDTO) {
        if (sysDeptService.checkDeptNameUnique(sysDeptDTO)) {
            return HttpResult.failMessage("新增部门「" + sysDeptDTO.getDeptName() + "」失败，部门名称已存在");
        }

        return HttpResult.ok(sysDeptService.insertOne(sysDeptDTO));
    }

    @PutMapping
    @Operation(summary = "部门修改", description = "修改部门")
    @PreAuthorize("hasAuthority('system:dept:edit')")
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysDeptDTO sysDeptDTO) {
        String deptId = sysDeptDTO.getDeptId();
        if (sysDeptDTO.getParentId().equals(deptId)) {
            return HttpResult.failMessage("修改部门「" + sysDeptDTO.getDeptName() + "」失败，上级部门不能是自己");
        }

        if (sysDeptService.checkDeptNameUnique(sysDeptDTO)) {
            return HttpResult.failMessage("修改部门「" + sysDeptDTO.getDeptName() + "」失败，部门名称已存在");
        }

        if (ColumnConstant.STATUS_EXCEPTION.equals(sysDeptDTO.getStatus())) {
            if (sysDeptService.listChildrenDeptCountById(deptId) > 0) {
                return HttpResult.failMessage("该部门包含未停用的子部门，不能禁用");
            }

            if (sysDeptService.checkDeptExistUser(deptId)) {
                return HttpResult.failMessage("该部门下已存在用户，不能禁用!");
            }
        }

        return HttpResult.ok(sysDeptService.updateOne(sysDeptDTO));
    }

    @DeleteMapping("/{id}/{deptId}")
    @Operation(summary = "部门删除", description = "通过主键删除部门")
    @PreAuthorize("hasAuthority('system:dept:remove')")
    public Response<Boolean> removeOne(@PathVariable Long id, @PathVariable String deptId) {
        if (sysDeptService.hasChild(deptId)) {
            return HttpResult.failMessage("存在下级部门，不允许删除");
        }

        if (sysDeptService.checkDeptExistUser(deptId)) {
            return HttpResult.failMessage("部门存在用户，不允许删除");
        }
        
        return HttpResult.ok(sysDeptService.removeOne(id));
    }

}
