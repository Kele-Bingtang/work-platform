package cn.youngkbt.ag.system.controller;

import cn.youngkbt.ag.system.model.dto.DataSourceDTO;
import cn.youngkbt.ag.system.model.vo.DataSourceVO;
import cn.youngkbt.ag.system.permission.annotation.TeamAuthorize;
import cn.youngkbt.ag.system.service.DataSourceService;
import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024/6/30 21:07:46
 * @note
 */
@RestController
@RequestMapping("/dataSource")
@RequiredArgsConstructor
public class DataSourceController {
    private final DataSourceService dataSourceService;

    @GetMapping("/listPage")
    @Operation(summary = "数据源列表查询（分页）", description = "通过条件查询数据源列表（分页）")
    public Response<TablePage<DataSourceVO>> listPage(@Validated(RestGroup.QueryGroup.class) DataSourceDTO dataSourceDTO, PageQuery pageQuery) {
        TablePage<DataSourceVO> dataSourceVOTablePage = dataSourceService.listPage(dataSourceDTO, pageQuery);
        return HttpResult.ok(dataSourceVOTablePage);
    }

    @GetMapping("/listSelect/{teamId}")
    @Operation(summary = "数据源下拉列表查询", description = "获取下拉框数据源列表")
    @TeamAuthorize(value = "#teamId", checkOwnerAndAdmin = true)
    public Response<List<DataSourceVO>> listSelect(@PathVariable String teamId) {
        List<DataSourceVO> dataSourceVOList = dataSourceService.listSelect(teamId);
        return HttpResult.ok(dataSourceVOList);
    }

    @PostMapping
    @Operation(summary = "数据源新增", description = "新增数据源")
    public Response<Boolean> addDataSource(@Validated(RestGroup.AddGroup.class) @RequestBody DataSourceDTO dataSourceDTO) {
        if (dataSourceService.checkDataSourceNameUnique(dataSourceDTO)) {
            return HttpResult.failMessage("新增数据源「" + dataSourceDTO.getDataSourceName() + "」失败，数据源名称已存在");
        }
        return HttpResult.okOrFail(dataSourceService.addDataSource(dataSourceDTO));
    }

    @PutMapping
    @Operation(summary = "数据源修改", description = "修改数据源")
    public Response<Boolean> editDataSource(@Validated(RestGroup.EditGroup.class) @RequestBody DataSourceDTO dataSourceDTO) {
        if (dataSourceService.checkDataSourceNameUnique(dataSourceDTO)) {
            return HttpResult.failMessage("新增数据源「" + dataSourceDTO.getDataSourceName() + "」失败，数据源名称已存在");
        }

        return HttpResult.okOrFail(dataSourceService.editDataSource(dataSourceDTO));
    }

    @DeleteMapping("/{dataSourceId}")
    @Operation(summary = "数据源删除", description = "删除数据源")
    public Response<Boolean> removeDataSource(@PathVariable String dataSourceId) {
        return HttpResult.okOrFail(dataSourceService.removeDataSource(dataSourceId));
    }

    @PostMapping("/testConnect")
    @Operation(summary = "数据源连接测试", description = "连接测试数据源")
    public Response<Boolean> testConnect(@RequestBody DataSourceDTO dataSourceDTO) {
        return HttpResult.okOrFail(dataSourceService.testConnect(dataSourceDTO));
    }
}
