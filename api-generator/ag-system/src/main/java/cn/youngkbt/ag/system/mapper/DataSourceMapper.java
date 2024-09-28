package cn.youngkbt.ag.system.mapper;

import cn.youngkbt.ag.system.model.po.DataSource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024-06-30 21:00:59
 * @note 针对表「t_data_source（数据源配置表）」的数据库操作Mapper
 */
public interface DataSourceMapper extends BaseMapper<DataSource> {
    
    List<DataSource> listByProjectId(@Param("projectId") String projectId);
}




