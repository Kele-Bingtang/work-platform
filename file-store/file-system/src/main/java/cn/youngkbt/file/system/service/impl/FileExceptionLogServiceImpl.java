package cn.youngkbt.file.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.youngkbt.file.system.model.po.FileExceptionLog;
import cn.youngkbt.file.system.service.FileExceptionLogService;
import cn.youngkbt.file.system.mapper.FileExceptionLogMapper;
import org.springframework.stereotype.Service;

/**
 * @author Kele-Bingtang
 * @date 2024-08-06 21:08:58
 * @note 针对表「t_file_exception_log（异常日志表）」的数据库操作 Service 实现
 */
@Service
public class FileExceptionLogServiceImpl extends ServiceImpl<FileExceptionLogMapper, FileExceptionLog> implements FileExceptionLogService {

}




