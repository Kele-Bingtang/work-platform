package cn.youngkbt.uac.sys.service;

import cn.youngkbt.core.event.LoginInfoEvent;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.uac.sys.model.dto.SysLoginLogDTO;
import cn.youngkbt.uac.sys.model.po.SysLoginLog;
import cn.youngkbt.uac.sys.model.vo.SysLoginLogVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_login_info(操作日志记录)】的数据库操作Service
 */
public interface SysLoginLogService extends IService<SysLoginLog> {

    /**
     * 记录登录日志
     *
     * @param loginInfoEvent 登录事件信息
     */
    void recordLoginLog(LoginInfoEvent loginInfoEvent);

    /**
     * 通过条件查询登录日志列表（支持分页）
     * @param sysLoginLogDTO 查询条件
     * @param pageQuery 分页条件
     * @return 登录日志列表
     */
    TablePage<SysLoginLogVO> listPage(SysLoginLogDTO sysLoginLogDTO, PageQuery pageQuery);
}
