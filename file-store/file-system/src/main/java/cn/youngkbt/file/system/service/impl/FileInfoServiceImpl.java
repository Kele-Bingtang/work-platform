package cn.youngkbt.file.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.youngkbt.file.system.model.po.FileInfo;
import cn.youngkbt.file.system.service.FileInfoService;
import cn.youngkbt.file.system.mapper.FileInfoMapper;
import org.springframework.stereotype.Service;

/**
 * @author Kele-Bingtang
 * @date 2024-08-06 21:08:58
 * @note 针对表「t_file_info（附件信息表）」的数据库操作 Service 实现
 */
@Service
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements FileInfoService {

}




