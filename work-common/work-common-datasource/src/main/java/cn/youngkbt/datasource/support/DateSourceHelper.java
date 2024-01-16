package cn.youngkbt.datasource.support;

import cn.youngkbt.datasource.properties.DataSourceProperties;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author Kele-Bingtang
 * @date 2023/12/26 21:03
 * @note
 */
@RequiredArgsConstructor
@Component
public class DateSourceHelper {
    private final DataSource dataSource;
    private final DefaultDataSourceCreator defaultDataSourceCreator;

    /**
     * 获取所有数据源信息
     */
    public Map<String, DataSource> getDataSource() {
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        return ds.getDataSources();
    }

    /**
     * 添加一个数据源
     */
    public void addDataSource(DataSourceProperties dataSourceProperties) {
        DataSourceProperty dataSourceProperty = new DataSourceProperty();
        BeanUtils.copyProperties(dataSourceProperties, dataSourceProperty);
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        DataSource newDataSource = defaultDataSourceCreator.createDataSource(dataSourceProperty);
        ds.addDataSource(dataSourceProperties.getDbName(), newDataSource);
    }

    /**
     * 删除一个数据源
     */
    public void removeDataSource(String dataSourceName) {
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        ds.removeDataSource(dataSourceName);
    }
}
