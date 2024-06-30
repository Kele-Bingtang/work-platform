package cn.youngkbt.ag.system.mapper.base;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kele-Bingtang
 * @date 2024/6/30 01:12:19
 * @note
 */
public interface SQLExecuteMapper {

    /**
     * 查询单个对象
     *
     * @param sql 查询 sql
     * @return 查询出的数据
     */
    @Select("${sql}")
    LinkedHashMap<String, Object> executeSelectOne(@Param("sql") String sql, Map<String, Object> params);

    /**
     * 查询对象列表
     *
     * @param sql 查询 sql
     * @return 查询出的数据
     */
    @Select("${sql}")
    List<LinkedHashMap<String, Object>> executeSelectList(@Param("sql") String sql, Map<String, Object> params);

    /**
     * 增删改操作
     *
     * @param sql 增删改 sql
     * @return 增删改操作成功条数
     */
    @Insert("${sql}")
    Integer executeOperate(@Param("sql") String sql, Map<String, Object> params);
}