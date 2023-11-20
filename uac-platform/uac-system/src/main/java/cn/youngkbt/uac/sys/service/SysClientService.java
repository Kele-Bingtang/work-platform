package cn.youngkbt.uac.sys.service;

import cn.youngkbt.uac.sys.model.po.SysClient;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Kele-Bingtang
 * @date 2023-59-13 22:59:31
 * @note 针对表【t_sys_client(客户端授权表)】的数据库操作Service
*/
public interface SysClientService extends IService<SysClient> {

    SysClient checkClientIdThenGet(String clientId);
}
