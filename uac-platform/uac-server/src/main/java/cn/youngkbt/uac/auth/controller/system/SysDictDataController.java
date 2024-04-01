package cn.youngkbt.uac.auth.controller.system;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.uac.sys.model.dto.SysDictDataDTO;
import cn.youngkbt.uac.sys.model.vo.SysDictDataVO;
import cn.youngkbt.uac.sys.service.SysDictDataService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "字典数据列表查询", description = "通过主键查询字典数据列表")
    public Response<SysDictDataVO> listById(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        SysDictDataVO sysDictDataVo = sysDictDataService.listById(id);
        return HttpResult.ok(sysDictDataVo);
    }

    @GetMapping("/list")
    @Operation(summary = "字典数据列表查询", description = "通过条件查询字典数据列表")
    public Response<List<SysDictDataVO>> list(SysDictDataDTO sysDictDataDTO) {
        List<SysDictDataVO> sysDictDataVOList = sysDictDataService.queryList(sysDictDataDTO);
        return HttpResult.ok(sysDictDataVOList);
    }

    @GetMapping("/listPage")
    @Operation(summary = "字典数据列表查询", description = "通过条件查询字典数据列表（支持分页）")
    public Response<TablePage<SysDictDataVO>> listPage(SysDictDataDTO sysDictDataDTO, PageQuery pageQuery) {
        TablePage<SysDictDataVO> tablePage = sysDictDataService.listPage(sysDictDataDTO, pageQuery);
        return HttpResult.ok(tablePage);
    }

    @PostMapping
    @Operation(summary = "字典数据新增", description = "新增字典数据")
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysDictDataDTO sysDictDataDTO) {
        return HttpResult.ok(sysDictDataService.insertOne(sysDictDataDTO));
    }

    @PutMapping
    @Operation(summary = "字典数据修改", description = "修改字典数据")
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysDictDataDTO sysDictDataDTO) {
        return HttpResult.ok(sysDictDataService.updateOne(sysDictDataDTO));
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "字典数据删除", description = "通过主键批量删除字典数据")
    public Response<Boolean> removeBatch(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return HttpResult.ok(sysDictDataService.removeBatch(List.of(ids)));
    }
}
