package cn.youngkbt.file.system.service;

import cn.youngkbt.file.system.model.dto.FileOperaLogDTO;
import cn.youngkbt.file.system.model.po.FileOperaLog;
import cn.youngkbt.file.system.model.vo.FileOperaLogVO;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024-08-06 21:08:58
 * @note 针对表「t_file_opera_log（附件信息日志表）」的数据库操作 Service
 */
public interface FileOperaLogService extends IService<FileOperaLog> {

    List<FileOperaLogVO> listAll(FileOperaLogDTO fileOperaLogDTO);
    
    TablePage<FileOperaLogVO> listPage(FileOperaLogDTO fileOperaLogDTO, PageQuery pageQuery);

    Boolean removeBatch(List<Long> ids);

    Boolean cleanAllOperaLog();

}
