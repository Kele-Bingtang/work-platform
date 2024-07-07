package cn.youngkbt.ag.core.helper;

import cn.youngkbt.core.exception.ServiceException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Kele-Bingtang
 * @date 2024/7/7 22:53:00
 * @note
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SqlHelper {

    /**
     * 添加 LIMIT 到 SQL
     *
     * @param selectSql 查询 SQL
     * @param limit     limit 数量
     * @param dbType    数据源类型
     * @return 添加了 limit 的 SQL
     */
    public static String addLimitToSql(String selectSql, int limit, String dbType) {
        String finalSql = "";

        if ("MySQL".equalsIgnoreCase(dbType) || "MariaDB".equalsIgnoreCase(dbType) || "PostgreSQL".equalsIgnoreCase(dbType) || "SQLite".equalsIgnoreCase(dbType)) {
            finalSql = "SELECT * FROM (" + selectSql + ") t LIMIT " + limit;
        } else if ("Oracle".equalsIgnoreCase(dbType)) {
            // Oracle 使用 ROW_NUMBER() OVER() 来实现 LIMIT，注意这需要原 SQL 有明确的排序规则
            finalSql = "SELECT * FROM (SELECT t.*, ROW_NUMBER() OVER (ORDER BY some_column) AS rn FROM (" + selectSql + ") t) WHERE rn <= " + limit;
        } else if ("SQLServer".equalsIgnoreCase(dbType)) {
            finalSql = "SELECT TOP (" + limit + ") * FROM (" + selectSql + ") AS limiter";
        } else {
            throw new ServiceException("不支持数据源类型: " + dbType);
        }

        return finalSql;
    }
}
