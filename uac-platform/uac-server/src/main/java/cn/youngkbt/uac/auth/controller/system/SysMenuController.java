package cn.youngkbt.uac.auth.controller.system;

import cn.hutool.core.lang.tree.Tree;
import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysMenuDto;
import cn.youngkbt.uac.sys.model.vo.SysMenuVo;
import cn.youngkbt.uac.sys.service.SysMenuService;
import jakarta.validation.constraints.NotEmpty;
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
    public Response<SysMenuVo> queryById(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        SysMenuVo sysMenuVo = sysMenuService.queryById(id);
        return HttpResult.ok(sysMenuVo);
    }

    /**
     * 菜单列表查询
     */
    @GetMapping("/list")
    public Response<List<SysMenuVo>> list(SysMenuDto sysMenuDto, PageQuery pageQuery) {
        List<SysMenuVo> sysMenuVoList = sysMenuService.queryListWithPage(sysMenuDto, pageQuery);
        return HttpResult.ok(sysMenuVoList);
    }

    /**
     * 菜单下拉值查询
     */
    @GetMapping("/treeSelect")
    public Response<List<Tree<Long>>> treeSelect(SysMenuDto sysMenuDto) {
        return null;
    }

    /**
     * 菜单新增
     */
    @PostMapping
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysMenuDto sysMenuDto) {
        if(!sysMenuService.checkMenuNameUnique(sysMenuDto)) {
            return HttpResult.failMessage("新增菜单「" + sysMenuDto.getMenuName() + "」失败，菜单名称已存在");
        }
        
        return HttpResult.ok(sysMenuService.insertOne(sysMenuDto));
    }

    /**
     * 菜单修改
     */
    @PutMapping
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysMenuDto sysMenuDto) {
        
        if(sysMenuDto.getParentId().equals(sysMenuDto.getMenuId())) {
            return HttpResult.failMessage("修改菜单「" + sysMenuDto.getMenuName() + "」失败，上级菜单不能是自己");
        }
        
        if(!sysMenuService.checkMenuNameUnique(sysMenuDto)) {
            return HttpResult.failMessage("修改菜单「" + sysMenuDto.getMenuName() + "」失败，菜单名称已存在");
        }
        return HttpResult.ok(sysMenuService.updateOne(sysMenuDto));
    }
    
    /**
     * 菜单删除
     */
    @DeleteMapping("/{id}")
    public Response<Boolean> removeOne(@NotEmpty(message = "主键不能为空") @PathVariable Long menuId) {
        if(sysMenuService.hasChild(menuId)) {
            return HttpResult.failMessage("存在下级菜单，不允许删除");
        }

        if (sysMenuService.checkMenuExistRole(menuId)) {
            return HttpResult.failMessage("菜单存在角色，不允许删除");
        }

        return HttpResult.ok(sysMenuService.removeOne(menuId));
    }
    /**
     * 菜单删除
     */
    @DeleteMapping("/batch/{ids}")
    public Response<Boolean> removeBatch(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return HttpResult.ok(sysMenuService.removeBatch(List.of(ids)));
    }
}
