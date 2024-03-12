package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysClientDTO;
import cn.youngkbt.uac.sys.model.po.SysClient;
import cn.youngkbt.uac.sys.model.vo.SysClientVO;
import cn.youngkbt.uac.sys.model.vo.extra.ClientTreeVO;
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

    SysClientVO listById(Long id);

    List<SysClientVO> queryListWithPage(SysClientDTO sysClientDto, PageQuery pageQuery);

    List<ClientTreeVO> listTreeList();

    boolean insertOne(SysClientDTO sysClientDto);

    boolean updateOne(SysClientDTO sysClientDto);

    boolean updateStatus(Long id, Integer status);

    boolean removeBatch(Collection<Long> ids);

}
