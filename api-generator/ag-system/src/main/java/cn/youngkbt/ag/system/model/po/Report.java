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
 * @date 2024-03-23 01:03:52
 * @note 报表
*/
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_report")
@Data
public class Report extends BaseDO {
    /**
     * 报表 ID
     */
    @TableField(fill = FieldFill.INSERT)
    @FieldValueFill(ValueStrategy.SNOWFLAKE)
    private String reportId;

    /**
     * 报表名称
     */
    private String reportTitle;

    /**
     * 报名描述
     */
    private String description;

    /**
     * 是否允许新增（0 不允许，1 允许）
     */
    private Integer allowAdd;

    /**
     * 是否允许编辑（0 不允许，1 允许）
     */
    private Integer allowEdit;

    /**
     * 是否允许删除（0 不允许，1 允许）
     */
    private Integer allowDelete;

    /**
     * 是否允许查询（0 不允许，1 允许）
     */
    private Integer allowFilter;

    /**
     * 是否允许导出（0 不允许，1 允许）
     */
    private Integer allowExport;

    /**
     * 报表是否出现行数（0 不允许，1 允许）
     */
    private Integer allowRow;

    /**
     * 弹出框宽度
     */
    private String dialogWidth;

    /**
     * 一页显示多少条数据
     */
    private Integer pageSize;

    /**
     * 是否开启图标，0 不开启，1 饼图，2 折线图
     */
    private Integer chartType;

    /**
     * 接口 ID
     */
    private Long serviceId;
}