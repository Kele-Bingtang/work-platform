package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.uac.core.log.event.OperaLogEvent;
import cn.youngkbt.uac.sys.model.dto.SysOperaLogDTO;
import cn.youngkbt.uac.sys.model.po.SysOperaLog;
import cn.youngkbt.uac.sys.model.vo.SysOperaLogVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_opera_log(操作日志记录)】的数据库操作Service
 */
public interface SysOperaLogService extends IService<SysOperaLog> {

    /**
     * 记录操作日志
     *
     * @param operaLogEvent 操作日志事件
     */
    void recordOperaLog(OperaLogEvent operaLogEvent);
        
    /**
     * 分页查询操作日志
     *
     * @param sysOperaLogDTO 操作日志DTO
     * @param pageQuery      分页查询
     * @return 操作日志VO
     */
    TablePage<SysOperaLogVO> listPage(SysOperaLogDTO sysOperaLogDTO, PageQuery pageQuery);

    /**
     * 批量删除操作日志
     *
     * @param ids 操作日志ID
     * @return 是否删除成功
     */
    Boolean removeBatch(List<Long> ids);

    /**
     * 清空所有操作日志
     *
     * @return 是否清空成功
     */
    Boolean cleanAllOperaLog();
}
