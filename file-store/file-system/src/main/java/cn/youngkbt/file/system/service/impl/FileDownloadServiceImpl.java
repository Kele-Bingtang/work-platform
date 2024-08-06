package cn.youngkbt.file.system.service.impl;

import cn.youngkbt.core.exception.ServiceException;
import cn.youngkbt.file.system.helper.FileHelper;
import cn.youngkbt.file.system.model.po.FileInfo;
import cn.youngkbt.file.system.service.FileDownloadService;
import cn.youngkbt.file.system.service.FileInfoService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2024/8/5 21:41:39
 * @note
 */
@Service
@RequiredArgsConstructor
public class FileDownloadServiceImpl implements FileDownloadService {
    private final FileInfoService fileInfoService;

    @Override
    public void downloadFileByFileKey(String fileKey, HttpServletResponse response) {
        FileInfo fileInfo = fileInfoService.getOne(Wrappers.<FileInfo>lambdaQuery()
                .eq(FileInfo::getFileKey, fileKey));
        
        if(Objects.isNull(fileInfo)) {
            throw new ServiceException("文件不存在");
        }

        FileHelper.getFileForResponse(fileInfo.getFilePath(), fileInfo.getFileName(), response, false);
    }
}
