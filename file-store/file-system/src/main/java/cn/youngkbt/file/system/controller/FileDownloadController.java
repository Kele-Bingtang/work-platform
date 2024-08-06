package cn.youngkbt.file.system.controller;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.file.system.service.FileDownloadService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/{fileKey}")
    public Response<Void> downloadFileByFileKey(@PathVariable String fileKey, HttpServletResponse response) {
        fileDownloadService.downloadFileByFileKey(fileKey, response);
        return HttpResult.okMessage("下载中");
    }
}
