package cn.youngkbt.uac.auth.controller.system;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysDictDataDto;
import cn.youngkbt.uac.sys.model.vo.SysDictDataVo;
import cn.youngkbt.uac.sys.service.SysDictDataService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023/12/4 16:15
 * @note
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dictData")
public class SysDictDataController {
    private final SysDictDataService sysDictDataService;

    @GetMapping("/{id}")
    public Response<SysDictDataVo> queryById(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        SysDictDataVo sysDictDataVo = sysDictDataService.queryById(id);
        return HttpResult.ok(sysDictDataVo);
    }

    /**
     * 客户端列表查询
     */
    @GetMapping("/list")
    public Response<List<SysDictDataVo>> list(SysDictDataDto sysDictDataDto, PageQuery pageQuery) {
        List<SysDictDataVo> sysDictDataVoList = sysDictDataService.queryListWithPage(sysDictDataDto, pageQuery);
        return HttpResult.ok(sysDictDataVoList);
    }

    /**
     * 客户端新增
     */
    @PostMapping
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysDictDataDto sysDictDataDto) {
        return HttpResult.ok(sysDictDataService.insertOne(sysDictDataDto));
    }

    /**
     * 客户端修改
     */
    @PutMapping
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysDictDataDto sysDictDataDto) {
        return HttpResult.ok(sysDictDataService.updateOne(sysDictDataDto));
    }

    /**
     * 客户端删除
     */
    @DeleteMapping("/{ids}")
    public Response<Boolean> removeOne(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return HttpResult.ok(sysDictDataService.removeOne(List.of(ids)));
    }
}
