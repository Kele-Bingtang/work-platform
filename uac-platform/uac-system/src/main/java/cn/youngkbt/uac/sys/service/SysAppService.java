package cn.youngkbt.uac.sys.service;

import cn.youngkbt.uac.sys.model.po.SysApp;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_app(应用表)】的数据库操作Service
 */
public interface SysAppService extends IService<SysApp> {

    SysApp checkAppIdThenGet(String appId);
}
