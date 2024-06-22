package cn.youngkbt.ag.system.model.po;

import cn.youngkbt.mp.annotation.FieldValueFill;
import cn.youngkbt.mp.annotation.ValueStrategy;
import cn.youngkbt.mp.base.BaseDO;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Kele-Bingtang
 * @date 2024-04-23 01:04:28
 * @note 服务
*/
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_service")
@Data
public class ServiceInfo extends BaseDO {
    /**
     * 服务 ID
     */
    @TableField(fill = FieldFill.INSERT)
    @FieldValueFill(ValueStrategy.SNOWFLAKE)
    private String serviceId;

    /**
     * 服务名
     */
    private String serviceName;

    /**
     * 服务地址
     */
    private String serviceUrl;

    /**
     * 服务完整地址
     */
    private String fullUrl;

    /**
     * 服务状态（0 禁用 1 启用）
     */
    private Integer serviceStatus;

    /**
     * 服务描述
     */
    private String description;

    /**
     * 接口的查询 SQL 语句
     */
    private String selectSql;

    /**
     * 执行查询语句的表名
     */
    private String selectTable;

    /**
     * 执行插入语句的表名
     */
    private String insertTable;

    /**
     * 执行更新语句的表名
     */
    private String updateTable;

    /**
     * 执行删除语句的表名
     */
    private String deleteTable;

    /**
     * 是否进行认证（0 不认证 1 认证）
     */
    private Integer isAuth;

    /**
     * 报表名称
     */
    private String reportTitle;

    /**
     * 接口版本号（修改一次 +1）
     */
    private Integer serviceVersion;

    /**
     * 目录的 ID
     */
    private Long categoryId;

    /**
     * 项目 ID
     */
    private Long projectId;

    /**
     * 团队 ID
     */
    private String teamId;

}