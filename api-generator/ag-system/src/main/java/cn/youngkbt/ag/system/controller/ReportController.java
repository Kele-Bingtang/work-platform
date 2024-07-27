package cn.youngkbt.ag.system.controller;

import cn.youngkbt.ag.system.model.dto.ReportDTO;
import cn.youngkbt.ag.system.model.vo.ReportDataVO;
import cn.youngkbt.ag.system.model.vo.ReportVO;
import cn.youngkbt.ag.system.permission.annotation.ProjectAuthorize;
import cn.youngkbt.ag.system.service.ReportService;
import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Kele-Bingtang
 * @date 2024/6/26 00:05:23
 * @note
 */
@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/getReportByServiceId/{serviceId}")
    @Operation(summary = "报表查询", description = "通过条件查询报表")
    public Response<ReportVO> getReportByServiceId(@PathVariable String serviceId) {
        ReportVO reportVO = reportService.getReportByServiceId(serviceId);
        return HttpResult.ok(reportVO);
    }

    @PutMapping
    @Operation(summary = "报表修改", description = "修改报表")
    @ProjectAuthorize(value = "#reportDTO.getProjectId()", checkReadAndWrite = true)
    public Response<Boolean> editReport(@Validated(RestGroup.EditGroup.class) @RequestBody ReportDTO reportDTO) {
        if (reportService.checkReportTitleUnique(reportDTO)) {
            return HttpResult.failMessage("编辑报表「" + reportDTO.getReportTitle() + "」失败，报表标题重复");
        }

        return HttpResult.okOrFail(reportService.editReport(reportDTO));
    }

    @GetMapping(value = "/listReportConfig/{serviceId}")
    @Operation(summary = "查询报表数据", description = "查询报表数据")
    public Response<ReportDataVO> listReportConfig(@PathVariable String serviceId) {
        ReportDataVO reportDataVO = reportService.listReportConfig(serviceId);
        return HttpResult.ok(reportDataVO);
    }
    
}
