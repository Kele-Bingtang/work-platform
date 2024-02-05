package cn.youngkbt.uac.auth.controller.system;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.vo.extra.RolePostVo;
import cn.youngkbt.uac.sys.model.dto.SysUserDto;
import cn.youngkbt.uac.sys.model.vo.SysUserVo;
import cn.youngkbt.uac.sys.service.SysUserService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023/12/4 19:58
 * @note
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/user")
public class SysUserController {

    private final SysUserService sysUserService;

    @GetMapping("/{id}")
    public Response<SysUserVo> queryById(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        SysUserVo sysUserVo = sysUserService.queryById(id);
        return HttpResult.ok(sysUserVo);
    }

    /**
     * 客户端列表查询
     */
    @GetMapping("/list")
    public Response<List<SysUserVo>> list(SysUserDto sysUserDto, PageQuery pageQuery) {
        List<SysUserVo> sysUserVoList = sysUserService.queryListWithPage(sysUserDto, pageQuery);
        return HttpResult.ok(sysUserVoList);
    }

    @GetMapping("/rolePostList")
    public Response<RolePostVo> rolePostList() {
        RolePostVo rolePostVo = sysUserService.rolePostList();
        return HttpResult.ok(rolePostVo);
    }

    /**
     * 客户端新增
     */
    @PostMapping
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysUserDto sysUserDto) {
        return HttpResult.ok(sysUserService.insertOne(sysUserDto));
    }

    /**
     * 客户端修改
     */
    @PutMapping
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysUserDto sysUserDto) {
        return HttpResult.ok(sysUserService.updateOne(sysUserDto));
    }

    /**
     * 客户端删除
     */
    @DeleteMapping("/{ids}")
    public Response<Boolean> removeOne(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return HttpResult.ok(sysUserService.removeOne(List.of(ids)));
    }
}
