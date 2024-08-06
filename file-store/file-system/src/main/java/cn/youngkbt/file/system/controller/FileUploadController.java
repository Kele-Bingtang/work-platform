package cn.youngkbt.file.system.controller;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.file.system.model.dto.UploadFileDTO;
import cn.youngkbt.file.system.model.vo.FileUploadSuccessVO;
import cn.youngkbt.file.system.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2024/8/6 21:18:29
 * @note
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/upload")
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @PostMapping
    public Response<List<FileUploadSuccessVO>> uploadFiles(@RequestParam("fileList") MultipartFile[] fileList, @Validated(RestGroup.AddGroup.class) UploadFileDTO uploadFileDTO) {
        if (Objects.isNull(fileList) || fileList.length == 0) {
            return HttpResult.failMessage("文件为空，请先上传");
        }

        List<FileUploadSuccessVO> fileUploadSuccessVOList = fileUploadService.uploadFiles(fileList, uploadFileDTO);
        return HttpResult.ok(fileUploadSuccessVOList);
    }
}
