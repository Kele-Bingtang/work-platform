package cn.youngkbt.uac.auth.controller.system;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysDictTypeDto;
import cn.youngkbt.uac.sys.model.vo.SysDictTypeVo;
import cn.youngkbt.uac.sys.service.SysDictTypeService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023/12/4 16:29
 * @note
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dictType")
public class SysDictTypeController {

    private final SysDictTypeService sysDictTypeService;

    @GetMapping("/{id}")
    public Response<SysDictTypeVo> listById(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        SysDictTypeVo sysDictTypeVo = sysDictTypeService.listById(id);
        return HttpResult.ok(sysDictTypeVo);
    }

    /**
     * 客户端列表查询
     */
    @GetMapping("/list")
    public Response<List<SysDictTypeVo>> list(SysDictTypeDto sysDictTypeDto, PageQuery pageQuery) {
        List<SysDictTypeVo> sysDictTypeVoList = sysDictTypeService.queryListWithPage(sysDictTypeDto, pageQuery);
        return HttpResult.ok(sysDictTypeVoList);
    }

    /**
     * 客户端新增
     */
    @PostMapping
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysDictTypeDto sysDictTypeDto) {
        return HttpResult.ok(sysDictTypeService.insertOne(sysDictTypeDto));
    }

    /**
     * 客户端修改
     */
    @PutMapping
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysDictTypeDto sysDictTypeDto) {
        return HttpResult.ok(sysDictTypeService.updateOne(sysDictTypeDto));
    }

    /**
     * 客户端删除
     */
    @DeleteMapping("/{ids}")
    public Response<Boolean> removeBatch(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return HttpResult.ok(sysDictTypeService.removeBatch(List.of(ids)));
    }
}
