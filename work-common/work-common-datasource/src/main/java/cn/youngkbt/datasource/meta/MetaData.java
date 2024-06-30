package cn.youngkbt.datasource.meta;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024/6/30 17:49:27
 * @note
 */
public interface MetaData {

    /**
     * 获取数据源 Catalog
     *
     * @param dataSourceName 数据源名称
     * @return 数据源 Catalog
     */
    List<Schema> getCatalogs(String dataSourceName);

    /**
     * 获取数据源 Schema
     *
     * @param dataSourceName 数据源名称
     * @param catalog        Catalog
     * @return 数据源 Schema
     */
    List<Schema> getSchemas(String dataSourceName, String catalog);

    /**
     * 获取数据表列表
     *
     * @param dataSourceName 数据源名称
     * @param catalog        Catalog
     * @param schema         模式
     * @return 数据表列表
     */
    List<Table> getTables(String dataSourceName, String catalog, String schema);

    /**
     * 获取数据表字段列表
     *
     * @param dataSourceName 数据源名称
     * @param catalog        Catalog
     * @param schema         模式
     * @param table          表名
     * @return 数据表字段列表
     */
    List<Column> getColumns(String dataSourceName, String catalog, String schema, String table);
}
