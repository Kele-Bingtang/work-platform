package cn.youngkbt.ag.system.controller;

import cn.youngkbt.ag.system.model.dto.ReportDTO;
import cn.youngkbt.ag.system.model.vo.ReportVO;
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

    @GetMapping("/listOne")
    @Operation(summary = "报表查询", description = "通过条件查询报表")
    public Response<ReportVO> listOne(@Validated(RestGroup.QueryGroup.class) ReportDTO reportDTO) {
        ReportVO reportVO = reportService.listOne(reportDTO);
        return HttpResult.ok(reportVO);
    }

    @PutMapping
    @Operation(summary = "报表修改", description = "修改报表")
    public Response<Boolean> editReport(@Validated(RestGroup.EditGroup.class) @RequestBody ReportDTO reportDTO) {
        if (reportService.checkReportTitleUnique(reportDTO)) {
            return HttpResult.failMessage("编辑报表「" + reportDTO.getReportTitle() + "」失败，报表标题重复");
        }

        return HttpResult.okOrFail(reportService.editReport(reportDTO));
    }
}
