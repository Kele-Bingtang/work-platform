package cn.youngkbt.uac.auth.controller.system;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysPostDto;
import cn.youngkbt.uac.sys.model.vo.SysPostVo;
import cn.youngkbt.uac.sys.service.SysPostService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023/12/4 17:33
 * @note
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/post")
public class SysPostController {
    private final SysPostService sysPostService;

    @GetMapping("/{id}")
    public Response<SysPostVo> queryById(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        SysPostVo sysPostVo = sysPostService.queryById(id);
        return HttpResult.ok(sysPostVo);
    }

    /**
     * 客户端列表查询
     */
    @GetMapping("/list")
    public Response<List<SysPostVo>> list(SysPostDto sysPostDto, PageQuery pageQuery) {
        List<SysPostVo> sysPostVoList = sysPostService.queryListWithPage(sysPostDto, pageQuery);
        return HttpResult.ok(sysPostVoList);
    }

    /**
     * 客户端新增
     */
    @PostMapping
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysPostDto sysPostDto) {
        return HttpResult.ok(sysPostService.insertOne(sysPostDto));
    }

    /**
     * 客户端修改
     */
    @PutMapping
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysPostDto sysPostDto) {
        return HttpResult.ok(sysPostService.updateOne(sysPostDto));
    }

    /**
     * 客户端删除
     */
    @DeleteMapping("/{ids}")
    public Response<Boolean> removeOne(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return HttpResult.ok(sysPostService.removeOne(List.of(ids)));
    }
}
