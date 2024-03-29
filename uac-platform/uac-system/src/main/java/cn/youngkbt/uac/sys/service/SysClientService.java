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

    /**
     * 检查客户端 ID 是否存在，如果存在则返回客户端信息
     *
     * @param clientId 客户端ID
     * @return 客户端信息
     */
    SysClient checkClientIdThenGet(String clientId);

    /**
     * 根据主键查询客户端信息
     *
     * @param id 主键
     * @return 客户端信息
     */
    SysClientVO listById(Long id);

    /**
     * 通过条件查询客户端信息
     *
     * @param sysClientDto 客户端信息
     * @param pageQuery    分页参数
     * @return 客户端信息
     */
    List<SysClientVO> listWithPage(SysClientDTO sysClientDto, PageQuery pageQuery);

    /**
     * 查询客户端树形列表
     *
     * @return 客户端树形列表
     */
    List<ClientTreeVO> listTreeList();

    /**
     * 新增客户端信息
     *
     * @param sysClientDto 客户端信息
     * @return 是否成功
     */
    boolean insertOne(SysClientDTO sysClientDto);

    /**
     * 修改客户端信息
     *
     * @param sysClientDto 客户端信息
     * @return 是否成功
     */
    boolean updateOne(SysClientDTO sysClientDto);

    /**
     * 修改客户端状态
     *
     * @param id     主键
     * @param status 状态
     * @return 是否成功
     */
    boolean updateStatus(Long id, Integer status);

    /**
     * 删除客户端信息
     *
     * @param ids 主键
     * @return 是否成功
     */
    boolean removeBatch(Collection<Long> ids);

}
