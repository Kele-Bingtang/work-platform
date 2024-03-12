package cn.youngkbt.uac.auth.controller.system;

import cn.hutool.core.lang.tree.Tree;
import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysMenuDTO;
import cn.youngkbt.uac.sys.model.vo.SysMenuVO;
import cn.youngkbt.uac.sys.model.vo.extra.MenuTree;
import cn.youngkbt.uac.sys.service.SysMenuService;
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
    public Response<SysMenuVO> listById(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        SysMenuVO sysMenuVo = sysMenuService.listById(id);
        return HttpResult.ok(sysMenuVo);
    }

    /**
     * 菜单列表查询
     */
    @GetMapping("/list")
    public Response<List<SysMenuVO>> list(SysMenuDTO sysMenuDto, PageQuery pageQuery) {
        List<SysMenuVO> sysMenuVOList = sysMenuService.queryListWithPage(sysMenuDto, pageQuery);
        return HttpResult.ok(sysMenuVOList);
    }

    /**
     * 菜单下拉值查询
     */
    @GetMapping("/treeSelect")
    public Response<List<Tree<String>>> listMenuTreeSelect(SysMenuDTO sysMenuDto) {
        List<Tree<String>> menuTreeList = sysMenuService.listMenuTreeSelect(sysMenuDto);
        return HttpResult.ok(menuTreeList);
    }

    @GetMapping("/treeTable")
    public Response<List<MenuTree>> listMenuTreeTable(SysMenuDTO sysMenuDto) {
        List<MenuTree> treeTable = sysMenuService.listMenuTreeTable(sysMenuDto);
        return HttpResult.ok(treeTable);
    }

    /**
     * 菜单新增
     */
    @PostMapping
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysMenuDTO sysMenuDto) {
        if (!sysMenuService.checkMenuNameUnique(sysMenuDto)) {
            return HttpResult.failMessage("新增菜单「" + sysMenuDto.getMenuName() + "」失败，菜单名称已存在");
        }

        return HttpResult.ok(sysMenuService.insertOne(sysMenuDto));
    }

    /**
     * 菜单修改
     */
    @PutMapping
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysMenuDTO sysMenuDto) {

        if (sysMenuDto.getParentId().equals(sysMenuDto.getMenuId())) {
            return HttpResult.failMessage("修改菜单「" + sysMenuDto.getMenuName() + "」失败，上级菜单不能是自己");
        }

        if (!sysMenuService.checkMenuNameUnique(sysMenuDto)) {
            return HttpResult.failMessage("修改菜单「" + sysMenuDto.getMenuName() + "」失败，菜单名称已存在");
        }
        return HttpResult.ok(sysMenuService.updateOne(sysMenuDto));
    }

    /**
     * 菜单删除
     */
    @DeleteMapping("/{id}/{menuId}")
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
