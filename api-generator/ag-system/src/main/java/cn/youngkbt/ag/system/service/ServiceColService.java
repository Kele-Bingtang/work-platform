package cn.youngkbt.ag.system.service;

import cn.youngkbt.ag.system.model.dto.ServiceColBatchUpdateDTO;
import cn.youngkbt.ag.system.model.dto.ServiceColDTO;
import cn.youngkbt.ag.system.model.dto.ServiceInfoDTO;
import cn.youngkbt.ag.system.model.po.ServiceCol;
import cn.youngkbt.ag.system.model.po.ServiceInfo;
import cn.youngkbt.ag.system.model.vo.ServiceColVO;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.sql.ResultSetMetaData;
import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024-06-22 00:15:48
 * @note 针对表「t_service_col（列配置项表）」的数据库操作 Service
 */
public interface ServiceColService extends IService<ServiceCol> {

    /**
     * 根据服务 ID 获取列配置项
     *
     * @param serviceId 服务 ID
     * @return 列配置项
     */
    List<ServiceColVO> listByServiceId(String serviceId);
    
    /**
     * 分页查询列配置项
     *
     * @param serviceColDTO 列配置项查询条件
     * @param pageQuery     分页查询条件
     * @return 列配置项列表
     */
    TablePage<ServiceColVO> listPage(ServiceColDTO serviceColDTO, PageQuery pageQuery);

    /**
     * 新增列配置项
     *
     * @param serviceColDTO 列配置项
     * @return 是否新增成功
     */
    boolean addServiceCol(ServiceColDTO serviceColDTO);

    /**
     * 修改列配置项
     *
     * @param serviceColDTO 列配置项
     * @return 是否修改成功
     */
    boolean editServiceCol(ServiceColDTO serviceColDTO);

    /**
     * 根据主键删除列配置项信息
     *
     * @param id 列配置项主键
     * @return 是否删除成功
     */
    boolean removeServiceColById(String id);

    /**
     * 根据服务 ID 删除所有的列配置项信息
     *
     * @param serviceId 服务 ID
     * @return 是否删除成功
     */
    boolean removeAllServiceColByServiceId(String serviceId);

    /**
     * 根据分类 ID 删除所有的列配置项信息
     *
     * @param categoryId 分类 ID
     * @return 是否删除成功
     */
    boolean removeAllServiceColByServiceIdByCategoryId(String categoryId);

    /**
     * 根据项目 ID 删除所有的列配置项信息
     *
     * @param projectId 项目 ID
     * @return 是否删除成功
     */
    boolean removeAllServiceColByServiceIdByProjectIdId(String projectId);

    /**
     * 根据团队 ID 删除所有的列配置项信息
     *
     * @param teamId 团队 ID
     * @return 是否删除成功
     */
    boolean removeAllServiceColByServiceIdByTeamId(String teamId);

    /**
     * 校验列配置项是否唯一
     *
     * @param serviceColDTO 列配置项
     * @return 是否唯一
     */
    boolean checkServiceColUnique(ServiceColDTO serviceColDTO);

    /**
     * 根据 SQL 查询出表所有的列配置项信息，并新增到数据库
     *
     * @param serviceInfoDTO 服务信息
     * @return 是否新增成功
     */
    Integer listColumnThenInsert(ServiceInfoDTO serviceInfoDTO);

    /**
     * 根据 SQL 重新生成列配置项
     *
     * @param serviceInfo 服务信息
     * @return 是否成功
     */
    Integer reGenCol(ServiceInfo serviceInfo);

    /**
     * 根据 SQL 删除无效的列配置项
     *
     * @param serviceInfo 服务信息
     * @return 是否成功
     */
    Integer removeInvalidCol(ServiceInfo serviceInfo);

    /**
     * 获取 ResultSetMetaData
     *
     * @param serviceInfo 服务信息
     * @return ResultSetMetaData
     */
    ResultSetMetaData getResultSetMetaData(ServiceInfo serviceInfo);

    /**
     * 检查列配置项是否存在
     *
     * @param serviceId 服务 ID
     * @return 是否存在
     */
    boolean checkExitCol(String serviceId);

    /**
     * 批量修改列配置项
     *
     * @param batchUpdateDTO 批量修改列配置项
     * @return 是否成功
     */
    boolean editBatchServiceCol(ServiceColBatchUpdateDTO batchUpdateDTO);

}
