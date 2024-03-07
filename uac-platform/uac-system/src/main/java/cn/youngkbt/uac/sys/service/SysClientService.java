package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysClientDto;
import cn.youngkbt.uac.sys.model.po.SysClient;
import cn.youngkbt.uac.sys.model.vo.SysClientVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-59-13 22:59:31
 * @note 针对表【t_sys_client(客户端授权表)】的数据库操作Service
 */
public interface SysClientService extends IService<SysClient> {

    SysClient checkClientIdThenGet(String clientId);

    SysClientVo listById(Long id);

    List<SysClientVo> queryListWithPage(SysClientDto sysClientDto, PageQuery pageQuery);

    List<SysClientVo> listTreeList();

    boolean insertOne(SysClientDto sysClientDto);

    boolean updateOne(SysClientDto sysClientDto);

    boolean updateStatus(Long id, Integer status);

    boolean removeBatch(Collection<Long> ids);

}
