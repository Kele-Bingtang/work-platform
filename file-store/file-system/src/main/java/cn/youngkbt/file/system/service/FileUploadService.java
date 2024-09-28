package cn.youngkbt.file.system.service;

import cn.youngkbt.file.system.model.dto.UploadFileDTO;
import cn.youngkbt.file.system.model.vo.FileUploadSuccessVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024/8/5 21:41:01
 * @note
 */
public interface FileUploadService {

    List<FileUploadSuccessVO> uploadFiles(MultipartFile[] fileList, UploadFileDTO uploadFileDTO);

    List<FileUploadSuccessVO> uploadBase64(UploadFileDTO uploadFileDTO);
}
