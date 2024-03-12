package cn.youngkbt.uac.auth.controller.system;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysRoleDTO;
import cn.youngkbt.uac.sys.model.vo.SysRoleVO;
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
    public Response<SysRoleVO> listById(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        SysRoleVO sysRoleVo = sysRoleService.listById(id);
        return HttpResult.ok(sysRoleVo);
    }

    /**
     * 客户端列表查询
     */
    @GetMapping("/list")
    public Response<List<SysRoleVO>> list(SysRoleDTO sysRoleDto, PageQuery pageQuery) {
        List<SysRoleVO> sysRoleVOList = sysRoleService.queryListWithPage(sysRoleDto, pageQuery);
        return HttpResult.ok(sysRoleVOList);
    }

    /**
     * 客户端新增
     */
    @PostMapping
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysRoleDTO sysRoleDto) {
        if (sysRoleService.checkRoleNameUnique(sysRoleDto)) {
            return HttpResult.failMessage("新增角色「" + sysRoleDto.getRoleName() + "」失败，角色名称已存在");
        } else if (sysRoleService.checkRoleCodeUnique(sysRoleDto)) {
            return HttpResult.failMessage("新增角色「" + sysRoleDto.getRoleName() + "」失败，角色权限已存在");
        }
        
        return HttpResult.ok(sysRoleService.insertOne(sysRoleDto));
    }

    /**
     * 客户端修改
     */
    @PutMapping
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysRoleDTO sysRoleDto) {
        if (sysRoleService.checkRoleNameUnique(sysRoleDto)) {
            return HttpResult.failMessage("修改角色「" + sysRoleDto.getRoleName() + "」失败，角色名称已存在");
        } else if (sysRoleService.checkRoleCodeUnique(sysRoleDto)) {
            return HttpResult.failMessage("修改角色「" + sysRoleDto.getRoleName() + "」失败，角色权限已存在");
        }
        
        return HttpResult.ok(sysRoleService.updateOne(sysRoleDto));
    }

    /**
     * 客户端删除
     */
    @DeleteMapping("/{ids}")
    public Response<Boolean> removeBatch(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return HttpResult.ok(sysRoleService.removeBatch(List.of(ids)));
    }
}
