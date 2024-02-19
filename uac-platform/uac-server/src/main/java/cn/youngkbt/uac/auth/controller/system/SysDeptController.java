package cn.youngkbt.uac.auth.controller.system;

import cn.hutool.core.lang.tree.Tree;
import cn.youngkbt.core.constants.ColumnConstant;
import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysDeptDto;
import cn.youngkbt.uac.sys.model.vo.SysDeptVo;
import cn.youngkbt.uac.sys.model.vo.extra.DeptTree;
import cn.youngkbt.uac.sys.service.SysDeptService;
import jakarta.validation.constraints.NotEmpty;
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
    public Response<SysDeptVo> queryById(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        SysDeptVo sysDeptVo = sysDeptService.queryById(id);
        return HttpResult.ok(sysDeptVo);
    }

    /**
     * 部门列表查询
     */
    @GetMapping("/list")
    public Response<List<SysDeptVo>> list(SysDeptDto sysDeptDto, PageQuery pageQuery) {
        List<SysDeptVo> sysDeptVoList = sysDeptService.queryListWithPage(sysDeptDto, pageQuery);
        return HttpResult.ok(sysDeptVoList);
    }

    @GetMapping("/deptTreeList")
    public Response<List<Tree<String>>> selectDeptTreeList(SysDeptDto sysDeptDto) {
        List<Tree<String>> treeList = sysDeptService.selectDeptTreeList(sysDeptDto);
        return HttpResult.ok(treeList);
    }

    @GetMapping("/deptTreeTable")
    public Response<List<DeptTree>> deptTreeTable(SysDeptDto sysDeptDto) {
        List<DeptTree> treeTable = sysDeptService.buildDeptTreeTable(sysDeptDto);
        return HttpResult.ok(treeTable);
    }

    @GetMapping("/parentDept")
    public Response<SysDeptVo> queryParentDeptByDeptId(String deptId) {
        SysDeptVo sysDeptVo = sysDeptService.queryParentDeptByDeptId(deptId);
        return HttpResult.ok(sysDeptVo);
    }

    @GetMapping("/deptNames")
    public Response<List<String>> queryDeptNamesByIds(List<String> ids) {
        List<String> deptNameList = sysDeptService.queryDeptNamesByIds(ids);
        return HttpResult.ok(deptNameList);
    }

    @GetMapping("/userCount")
    public Response<Integer> getDeptUserCount(String deptId) {
        Integer deptsUserCount = sysDeptService.getDeptUserCount(deptId);
        return HttpResult.ok(deptsUserCount);
    }

    /**
     * 部门新增
     */
    @PostMapping
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysDeptDto sysDeptDto) {
        if (sysDeptService.checkDeptNameUnique(sysDeptDto).equals(Boolean.FALSE)) {
            return HttpResult.failMessage("新增部门'" + sysDeptDto.getDeptName() + "'失败，部门名称已存在");
        }

        return HttpResult.ok(sysDeptService.insertOne(sysDeptDto));
    }

    /**
     * 部门修改
     */
    @PutMapping
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysDeptDto sysDeptDto) {
        String deptId = sysDeptDto.getDeptId();
        if (sysDeptDto.getParentId().equals(deptId)) {
            return HttpResult.failMessage("修改部门'" + sysDeptDto.getDeptName() + "'失败，上级部门不能是自己");
        }

        if (sysDeptService.checkDeptNameUnique(sysDeptDto).equals(Boolean.FALSE)) {
            return HttpResult.failMessage("修改部门'" + sysDeptDto.getDeptName() + "'失败，部门名称已存在");
        }

        if (ColumnConstant.STATUS_EXCEPTION.equals(sysDeptDto.getStatus())) {
            if (sysDeptService.queryChildrenDeptCountById(deptId) > 0) {
                return HttpResult.failMessage("该部门包含未停用的子部门，不能禁用");
            }

            if (sysDeptService.checkDeptExistUser(deptId).equals(Boolean.TRUE)) {
                return HttpResult.failMessage("该部门下已存在用户，不能禁用!");
            }
        }

        return HttpResult.ok(sysDeptService.updateOne(sysDeptDto));
    }

    /**
     * 部门删除
     */
    @DeleteMapping("/{deptId}")
    public Response<Boolean> removeOne(@NotEmpty(message = "部门 ID 不能为空") @PathVariable String deptId) {
        if (sysDeptService.hasChild(deptId)) {
            return HttpResult.failMessage("存在下级部门，不允许删除");
        }

        if (sysDeptService.checkDeptExistUser(deptId)) {
            return HttpResult.failMessage("部门存在用户，不允许删除");
        }
        return HttpResult.ok(sysDeptService.removeOne(deptId));
    }

    /**
     * 客户端删除
     */
    @DeleteMapping("/batch/{ids}")
    public Response<Boolean> removeBatch(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return HttpResult.ok(sysDeptService.removeBatch(List.of(ids)));
    }
}
