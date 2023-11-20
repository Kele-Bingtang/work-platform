package cn.youngkbt.uac.sys.model.po;

import cn.youngkbt.mp.annotation.FieldValueFill;
import cn.youngkbt.mp.annotation.ValueStrategy;
import cn.youngkbt.mp.base.BaseDO;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Kele-Bingtang
 * @date 2023-20-12 00:20:10
 * @note 应用信息
*/
@TableName("t_sys_app")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysApp extends BaseDO {
    /**
     * 应用 ID
     */
    @TableField(fill = FieldFill.INSERT)
    @FieldValueFill(ValueStrategy.SNOWFLAKE)
    private String appId;

    /**
     * 应用码
     */
    private String appCode;

    /**
     * 应用名
     */
    private String appName;

    /**
     * 应用介绍
     */
    private String intro;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 授权类型
     */
    private String grantTypes;

    /**
     * 租户编号
     */
    private String tenantId;

    /**
     * 部门 ID
     */
    private String deptId;

    /**
     * 客户端 ID
     */
    private String clientId;

}