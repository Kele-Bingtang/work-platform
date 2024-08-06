package cn.youngkbt.file.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.youngkbt.file.system.model.po.FileOperaLog;
import cn.youngkbt.file.system.service.FileOperaLogService;
import cn.youngkbt.file.system.mapper.FileOperaLogMapper;
import org.springframework.stereotype.Service;

/**
 * @author Kele-Bingtang
 * @note 针对表「t_file_opera_log（附件信息日志表）」的数据库操作 Service 实现
 * @date 2024-08-06 21:08:58
 */
@Service
public class FileOperaLogServiceImpl extends ServiceImpl<FileOperaLogMapper, FileOperaLog> implements FileOperaLogService {

}




