package cn.youngkbt.uac.auth.controller.system;

import cn.hutool.core.lang.tree.Tree;
import cn.youngkbt.core.constants.ColumnConstant;
import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysDeptDTO;
import cn.youngkbt.uac.sys.model.vo.SysDeptVO;
import cn.youngkbt.uac.sys.model.vo.extra.DeptTree;
import cn.youngkbt.uac.sys.service.SysDeptService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/{id}")
    @Operation(summary = "部门列表查询", description = "通过主键查询部门")
    public Response<SysDeptVO> listById(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        SysDeptVO sysDeptVo = sysDeptService.listById(id);
        return HttpResult.ok(sysDeptVo);
    }

    /**
     * 部门列表查询
     */
    @GetMapping("/list")
    @Operation(summary = "部门列表查询", description = "通过部门条件查询部门列表（支持分页）")
    public Response<List<SysDeptVO>> list(SysDeptDTO sysDeptDto, PageQuery pageQuery) {
        List<SysDeptVO> sysDeptVOList = sysDeptService.listWithPage(sysDeptDto, pageQuery);
        return HttpResult.ok(sysDeptVOList);
    }

    @GetMapping("/treeList")
    @Operation(summary = "部门树形结构查询", description = "通过部门条件查询部门树形结构")
    public Response<List<Tree<String>>> listDeptTreeList(SysDeptDTO sysDeptDto) {
        List<Tree<String>> deptTreeList = sysDeptService.listDeptTreeList(sysDeptDto);
        return HttpResult.ok(deptTreeList);
    }

    @GetMapping("/treeTable")
    @Operation(summary = "部门树形表格查询", description = "通过部门条件查询部门树形表格")
    public Response<List<DeptTree>> listDeptTreeTable(SysDeptDTO sysDeptDto) {
        List<DeptTree> treeTable = sysDeptService.listDeptTreeTable(sysDeptDto);
        return HttpResult.ok(treeTable);
    }

    @GetMapping("/parentDept")
    @Operation(summary = "部门父级部门查询", description = "通过部门 ID 查询父级部门")
    public Response<SysDeptVO> listParentDeptByDeptId(String deptId) {
        SysDeptVO sysDeptVo = sysDeptService.listParentDeptByDeptId(deptId);
        return HttpResult.ok(sysDeptVo);
    }

    @GetMapping("/deptNames")
    @Operation(summary = "部门名称查询", description = "通过部门 ID 列表查询部门名称")
    public Response<List<String>> listDeptNamesByIds(List<String> ids) {
        List<String> deptNameList = sysDeptService.listDeptNamesByIds(ids);
        return HttpResult.ok(deptNameList);
    }

    @GetMapping("/userCount")
    @Operation(summary = "部门用户数量查询", description = "通过部门 ID 查询部门用户数量")
    public Response<Integer> getDeptUserCount(String deptId) {
        Integer deptUserCount = sysDeptService.getDeptUserCount(deptId);
        return HttpResult.ok(deptUserCount);
    }

    @PostMapping
    @Operation(summary = "部门新增", description = "新增部门")
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysDeptDTO sysDeptDto) {
        if (sysDeptService.checkDeptNameUnique(sysDeptDto)) {
            return HttpResult.failMessage("新增部门「" + sysDeptDto.getDeptName() + "」失败，部门名称已存在");
        }

        return HttpResult.ok(sysDeptService.insertOne(sysDeptDto));
    }

    @PutMapping
    @Operation(summary = "部门修改", description = "修改部门")
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysDeptDTO sysDeptDto) {
        String deptId = sysDeptDto.getDeptId();
        if (sysDeptDto.getParentId().equals(deptId)) {
            return HttpResult.failMessage("修改部门「" + sysDeptDto.getDeptName() + "」失败，上级部门不能是自己");
        }

        if (sysDeptService.checkDeptNameUnique(sysDeptDto)) {
            return HttpResult.failMessage("修改部门「" + sysDeptDto.getDeptName() + "」失败，部门名称已存在");
        }

        if (ColumnConstant.STATUS_EXCEPTION.equals(sysDeptDto.getStatus())) {
            if (sysDeptService.listChildrenDeptCountById(deptId) > 0) {
                return HttpResult.failMessage("该部门包含未停用的子部门，不能禁用");
            }

            if (sysDeptService.checkDeptExistUser(deptId)) {
                return HttpResult.failMessage("该部门下已存在用户，不能禁用!");
            }
        }

        return HttpResult.ok(sysDeptService.updateOne(sysDeptDto));
    }

    @DeleteMapping("/{id}/{deptId}")
    @Operation(summary = "部门删除", description = "通过主键删除部门")
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
