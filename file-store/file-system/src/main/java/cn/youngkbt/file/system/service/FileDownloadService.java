package cn.youngkbt.file.system.service;

import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Kele-Bingtang
 * @date 2024/8/5 21:41:32
 * @note
 */
public interface FileDownloadService {
    /**
     * 根据 fileKey 下载文件
     * @param fileKey 文件唯一标识
     */
    void downloadFileByFileKey(String fileKey, HttpServletResponse response);

    // String getDownloadFileUrl();
    // void downloadFileByUrl();
}
