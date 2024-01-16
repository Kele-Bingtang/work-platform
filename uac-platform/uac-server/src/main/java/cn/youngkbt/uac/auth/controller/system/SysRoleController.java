package cn.youngkbt.uac.auth.controller.system;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysRoleDto;
import cn.youngkbt.uac.sys.model.vo.SysRoleVo;
import cn.youngkbt.uac.sys.service.SysRoleService;
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
    public Response<SysRoleVo> queryById(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        SysRoleVo sysRoleVo = sysRoleService.queryById(id);
        return HttpResult.ok(sysRoleVo);
    }

    /**
     * 客户端列表查询
     */
    @GetMapping("/list")
    public Response<List<SysRoleVo>> list(SysRoleDto sysRoleDto, PageQuery pageQuery) {
        List<SysRoleVo> sysRoleVoList = sysRoleService.queryListWithPage(sysRoleDto, pageQuery);
        return HttpResult.ok(sysRoleVoList);
    }

    /**
     * 客户端新增
     */
    @PostMapping
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysRoleDto sysRoleDto) {
        return HttpResult.ok(sysRoleService.insertOne(sysRoleDto));
    }

    /**
     * 客户端修改
     */
    @PutMapping
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysRoleDto sysRoleDto) {
        return HttpResult.ok(sysRoleService.updateOne(sysRoleDto));
    }

    /**
     * 客户端删除
     */
    @DeleteMapping("/{ids}")
    public Response<Boolean> removeOne(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return HttpResult.ok(sysRoleService.removeOne(List.of(ids)));
    }
}
