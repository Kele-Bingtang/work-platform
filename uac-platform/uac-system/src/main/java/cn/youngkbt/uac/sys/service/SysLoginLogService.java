package cn.youngkbt.uac.sys.service;

import cn.youngkbt.core.event.LoginInfoEvent;
import cn.youngkbt.uac.sys.model.po.SysLoginLog;
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
}
