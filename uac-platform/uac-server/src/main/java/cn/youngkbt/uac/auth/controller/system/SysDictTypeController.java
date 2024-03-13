package cn.youngkbt.uac.auth.controller.system;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysDictTypeDTO;
import cn.youngkbt.uac.sys.model.vo.SysDictTypeVO;
import cn.youngkbt.uac.sys.service.SysDictTypeService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "字典类型列表查询", description = "通过主键查询字典类型列表")
    public Response<SysDictTypeVO> listById(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        SysDictTypeVO sysDictTypeVo = sysDictTypeService.listById(id);
        return HttpResult.ok(sysDictTypeVo);
    }

    @GetMapping("/list")
    @Operation(summary = "字典类型列表查询", description = "通过应用条件查询字典类型列表（支持分页）")
    public Response<List<SysDictTypeVO>> list(SysDictTypeDTO sysDictTypeDto, PageQuery pageQuery) {
        List<SysDictTypeVO> sysDictTypeVOList = sysDictTypeService.listWithPage(sysDictTypeDto, pageQuery);
        return HttpResult.ok(sysDictTypeVOList);
    }
    
    @PostMapping
    @Operation(summary = "字典类型列表新增", description = "新增字典类型列表")
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysDictTypeDTO sysDictTypeDto) {
        return HttpResult.ok(sysDictTypeService.insertOne(sysDictTypeDto));
    }

    @PutMapping
    @Operation(summary = "字典类型列表修改", description = "修改字典类型列表")
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysDictTypeDTO sysDictTypeDto) {
        return HttpResult.ok(sysDictTypeService.updateOne(sysDictTypeDto));
    }

    /**
     * 客户端删除
     */
    @DeleteMapping("/{ids}")
    @Operation(summary = "字典类型列表删除", description = "通过主键批量删除字典类型列表")
    public Response<Boolean> removeBatch(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return HttpResult.ok(sysDictTypeService.removeBatch(List.of(ids)));
    }
}
