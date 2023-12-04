package cn.youngkbt.uac.auth.controller.system;

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
     * 客户端列表查询
     */
    @GetMapping("/list")
    public Response<List<SysMenuVo>> list(SysMenuDto sysMenuDto, PageQuery pageQuery) {
        List<SysMenuVo> sysMenuVoList = sysMenuService.queryListWithPage(sysMenuDto, pageQuery);
        return HttpResult.ok(sysMenuVoList);
    }

    /**
     * 客户端新增
     */
    @PostMapping
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysMenuDto sysMenuDto) {
        return HttpResult.ok(sysMenuService.insertOne(sysMenuDto));
    }

    /**
     * 客户端修改
     */
    @PutMapping
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysMenuDto sysMenuDto) {
        return HttpResult.ok(sysMenuService.updateOne(sysMenuDto));
    }

    /**
     * 客户端删除
     */
    @DeleteMapping("/{ids}")
    public Response<Boolean> removeOne(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return HttpResult.ok(sysMenuService.removeOne(List.of(ids)));
    }
}
