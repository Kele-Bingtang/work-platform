package cn.youngkbt.file.system.controller;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.file.system.aspect.app.annotation.AppAuthorize;
import cn.youngkbt.file.system.aspect.log.annotation.OperateLog;
import cn.youngkbt.file.system.aspect.log.enums.OperatorType;
import cn.youngkbt.file.system.model.dto.UploadFileDTO;
import cn.youngkbt.file.system.model.vo.FileUploadSuccessVO;
import cn.youngkbt.file.system.service.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "文件上传", description = "上传文件")
    @AppAuthorize("#uploadFileDTO.getAppId()")
    @OperateLog(operatorType = OperatorType.UPLOAD, appId = "#uploadFileDTO.getAppId()")
    public Response<List<FileUploadSuccessVO>> uploadFiles(@RequestParam("fileList") MultipartFile[] fileList, @Validated(RestGroup.AddGroup.class) UploadFileDTO uploadFileDTO) {
        if (Objects.isNull(fileList) || fileList.length == 0) {
            return HttpResult.failMessage("文件为空，请先上传");
        }

        List<FileUploadSuccessVO> fileUploadSuccessVOList = fileUploadService.uploadFiles(fileList, uploadFileDTO);
        return HttpResult.ok(fileUploadSuccessVOList);
    }

    @PostMapping("/base64")
    @Operation(summary = "图片 Base64 上传", description = "上传图片 Base64")
    @AppAuthorize("#uploadFileDTO.getAppId()")
    @OperateLog(operatorType = OperatorType.UPLOAD, appId = "#uploadFileDTO.getAppId()")
    public Response<List<FileUploadSuccessVO>> uploadBase64(@RequestBody @Validated(RestGroup.AddGroup.class) UploadFileDTO uploadFileDTO) {
        List<String> base64Image = uploadFileDTO.getBase64Images();
        if (Objects.isNull(base64Image) || base64Image.isEmpty()) {
            return HttpResult.failMessage("Base64 图片为空，请先上传");
        }

        return HttpResult.ok(fileUploadService.uploadBase64(uploadFileDTO));
    }
}
