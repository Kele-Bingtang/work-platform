package cn.youngkbt.uac.auth.controller.system;

import cn.hutool.core.lang.tree.Tree;
import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.uac.sys.model.dto.SysMenuDTO;
import cn.youngkbt.uac.sys.model.vo.SysMenuVO;
import cn.youngkbt.uac.sys.service.SysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023/12/4 16:41
 * @note
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/menu")
public class SysMenuController {
    private final SysMenuService sysMenuService;

    @GetMapping("/{id}")
    @Operation(summary = "菜单列表查询", description = "通过主键查询菜单列表")
    public Response<SysMenuVO> listById(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        SysMenuVO sysMenuVo = sysMenuService.listById(id);
        return HttpResult.ok(sysMenuVo);
    }

    @GetMapping("/list")
    @Operation(summary = "菜单列表查询", description = "通过查询条件查询菜单列表")
    public Response<List<SysMenuVO>> list(SysMenuDTO sysMenuDTO) {
        List<SysMenuVO> sysMenuVOList = sysMenuService.queryList(sysMenuDTO);
        return HttpResult.ok(sysMenuVOList);
    }

    @GetMapping("/listPage")
    @Operation(summary = "菜单列表查询", description = "通过查询条件查询菜单列表（支持分页）")
    public Response<TablePage<SysMenuVO>> listPage(SysMenuDTO sysMenuDTO, PageQuery pageQuery) {
        TablePage<SysMenuVO> tablePage = sysMenuService.listPage(sysMenuDTO, pageQuery);
        return HttpResult.ok(tablePage);
    }

    @GetMapping("/treeSelect")
    @Operation(summary = "菜单下拉值查询", description = "通过查询条件查询菜单下拉值（下拉框查询使用）")
    public Response<List<Tree<String>>> listMenuTreeSelect(SysMenuDTO sysMenuDTO) {
        List<Tree<String>> menuTreeList = sysMenuService.listMenuTreeSelect(sysMenuDTO);
        return HttpResult.ok(menuTreeList);
    }

    @GetMapping("/treeTable")
    @Operation(summary = "菜单树表查询", description = "通过查询条件查询菜单树表")
    public Response<List<SysMenuVO>> listMenuTreeTable(SysMenuDTO sysMenuDTO) {
        List<SysMenuVO> treeTable = sysMenuService.listMenuTreeTable(sysMenuDTO);
        return HttpResult.ok(treeTable);
    }

    @PostMapping
    @Operation(summary = "菜单新增", description = "新增菜单")
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysMenuDTO sysMenuDTO) {
        if (!sysMenuService.checkMenuNameUnique(sysMenuDTO)) {
            return HttpResult.failMessage("新增菜单「" + sysMenuDTO.getMenuName() + "」失败，菜单名称已存在");
        }

        return HttpResult.ok(sysMenuService.insertOne(sysMenuDTO));
    }

    @PutMapping
    @Operation(summary = "菜单修改", description = "修改菜单")
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysMenuDTO sysMenuDTO) {

        if (sysMenuDTO.getParentId().equals(sysMenuDTO.getMenuId())) {
            return HttpResult.failMessage("修改菜单「" + sysMenuDTO.getMenuName() + "」失败，上级菜单不能是自己");
        }

        if (!sysMenuService.checkMenuNameUnique(sysMenuDTO)) {
            return HttpResult.failMessage("修改菜单「" + sysMenuDTO.getMenuName() + "」失败，菜单名称已存在");
        }
        return HttpResult.ok(sysMenuService.updateOne(sysMenuDTO));
    }

    @DeleteMapping("/{id}/{menuId}")
    @Operation(summary = "菜单删除", description = "通过主键删除菜单")
    public Response<Boolean> removeOne(@PathVariable Long id, @PathVariable String menuId) {
        if (sysMenuService.hasChild(menuId)) {
            return HttpResult.failMessage("存在下级菜单，不允许删除");
        }

        if (sysMenuService.checkMenuExistRole(menuId)) {
            return HttpResult.failMessage("菜单存在角色，不允许删除");
        }

        return HttpResult.ok(sysMenuService.removeOne(id));
    }
}
