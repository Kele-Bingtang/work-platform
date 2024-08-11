package cn.youngkbt.file.system.controller;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.file.system.aspect.app.annotation.AppAuthorize;
import cn.youngkbt.file.system.aspect.log.annotation.OperateLog;
import cn.youngkbt.file.system.aspect.log.enums.OperatorType;
import cn.youngkbt.file.system.service.FileDownloadService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Kele-Bingtang
 * @date 2024/8/6 21:17:04
 * @note
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/download")
public class FileDownloadController {

    private final FileDownloadService fileDownloadService;

    @PostMapping("/{appId}/{fileKey}")
    @Operation(summary = "文件下载", description = "下载文件")
    @AppAuthorize("#appId")
    @OperateLog(operatorType = OperatorType.DOWNLOAD, appId = "#appId", fileKey = "#fileKey")
    public Response<Void> downloadFileByFileKey(@PathVariable String appId, @PathVariable String fileKey, 
                                                @RequestParam(required = false, defaultValue = "false") boolean isBrowse, HttpServletResponse response) {
        fileDownloadService.downloadFileByFileKey(appId, fileKey, isBrowse, response);
        return HttpResult.okMessage("下载中");
    }
}
