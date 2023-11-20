package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.uac.sys.mapper.SysClientMapper;
import cn.youngkbt.uac.sys.model.po.SysClient;
import cn.youngkbt.uac.sys.service.SysClientService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Kele-Bingtang
 * @date 2023-59-13 22:59:42
 * @note 针对表【t_sys_client(客户端授权表)】的数据库操作Service实现
*/
@Service
public class SysClientServiceImpl extends ServiceImpl<SysClientMapper, SysClient> implements SysClientService{

    @Override
    public SysClient checkClientIdThenGet(String clientId) {
        return baseMapper.selectOne(Wrappers.<SysClient>lambdaQuery().eq(SysClient::getClientId, clientId));
    }
}




