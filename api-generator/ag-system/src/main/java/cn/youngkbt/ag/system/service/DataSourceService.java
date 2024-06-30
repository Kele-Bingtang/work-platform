package cn.youngkbt.ag.system.service;

import cn.youngkbt.ag.system.model.dto.DataSourceDTO;
import cn.youngkbt.ag.system.model.po.DataSource;
import cn.youngkbt.ag.system.model.vo.DataSourceVO;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author admin
 * @date 2024-06-30 21:00:59
 * @note 针对表「t_data_source（数据源配置表）」的数据库操作 Service
 */
public interface DataSourceService extends IService<DataSource> {

    /**
     * 通过条件查询数据源列表（分页）
     *
     * @param dataSourceDTO 数据源查询条件
     * @param pageQuery     分页信息
     * @return 数据源列表
     */
    TablePage<DataSourceVO> listPage(DataSourceDTO dataSourceDTO, PageQuery pageQuery);

    /**
     * 获取下拉框数据源列表
     *
     * @param teamId 团队 ID
     * @return 数据源列表
     */
    List<DataSourceVO> listSelect(String teamId);

    /**
     * 新增数据源
     *
     * @param dataSourceDTO 数据源信息
     * @return 是否新增成功
     */
    Boolean addDataSource(DataSourceDTO dataSourceDTO);

    /**
     * 修改数据源
     *
     * @param dataSourceDTO 数据源信息
     * @return 是否修改成功
     */
    Boolean editDataSource(DataSourceDTO dataSourceDTO);

    /**
     * 删除数据源
     *
     * @param dataSourceId 数据源 ID
     * @return 是否删除成功
     */
    Boolean removeDataSource(String dataSourceId);

    /**
     * 校验数据源名称是否唯一
     *
     * @param dataSourceDTO 数据源信息
     * @return 是否唯一
     */
    boolean checkDataSourceNameUnique(DataSourceDTO dataSourceDTO);

    /**
     * 连接测试数据源
     *
     * @param dataSourceDTO 数据源信息
     * @return 是否连接成功
     */
    boolean testConnect(DataSourceDTO dataSourceDTO);

}
