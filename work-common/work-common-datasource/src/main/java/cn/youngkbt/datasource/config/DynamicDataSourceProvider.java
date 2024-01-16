package cn.youngkbt.datasource.config;

import cn.youngkbt.datasource.properties.DataSourceProperties;
import cn.youngkbt.datasource.support.DataSourceConstants;
import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.provider.AbstractJdbcDataSourceProvider;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kele-Bingtang
 * @date 2023/12/26 21:01
 * @note 项目初始化时，从数据库中获取数据源信息进行加载
 */
public class DynamicDataSourceProvider extends AbstractJdbcDataSourceProvider {
    private final DataSourceProperties dataSourceProperties;

    public DynamicDataSourceProvider(DefaultDataSourceCreator defaultDataSourceCreator, DataSourceProperties dataSourceProperties) {
        super(defaultDataSourceCreator, dataSourceProperties.getDriverClassName(), dataSourceProperties.getUrl(), dataSourceProperties.getUsername(), dataSourceProperties.getPassword());
        this.dataSourceProperties = dataSourceProperties;
    }

    @Override
    protected Map<String, DataSourceProperty> executeStmt(Statement statement) throws SQLException {
        ResultSet rs = statement.executeQuery(dataSourceProperties.getQueryDsSql());
        Map<String, DataSourceProperty> map = new HashMap<>(8);
        
        while (rs.next()) {
            String name = rs.getString(DataSourceConstants.DS_NAME);
            // 不加载 master 数据源
            if (!name.equals(DataSourceConstants.DS_MASTER)) {
                String username = rs.getString(DataSourceConstants.DS_USER_NAME);
                String password = rs.getString(DataSourceConstants.DS_USER_PWD);
                String url = rs.getString(DataSourceConstants.DS_JDBC_URL);
                String driverClassName = rs.getString(DataSourceConstants.DS_DRIVER_CLASS_NAME);
                DataSourceProperty property = new DataSourceProperty();
                property.setUsername(username);
                property.setLazy(false);
                property.setPassword(password);
                property.setUrl(url);
                property.setDriverClassName(driverClassName);
                map.put(name, property);
            }
        }

        // 添加默认主数据源
        DataSourceProperty property = new DataSourceProperty();
        property.setUsername(dataSourceProperties.getUsername());
        property.setPassword(dataSourceProperties.getPassword());
        property.setUrl(dataSourceProperties.getUrl());
        property.setDriverClassName(dataSourceProperties.getDriverClassName());
        property.setLazy(true);
        map.put(DataSourceConstants.DS_MASTER, property);
        return map;
    }
}