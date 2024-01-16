package cn.youngkbt.uac.sys.model.po;

import cn.youngkbt.mp.annotation.FieldValueFill;
import cn.youngkbt.mp.annotation.ValueStrategy;
import cn.youngkbt.mp.base.BaseDO;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Kele-Bingtang
 * @date 2023-24-12 00:24:45
 * @note 应用角色信息
*/
@TableName("t_sys_role")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseDO {
    /**
     * 角色 ID
     */
    @TableField(fill = FieldFill.INSERT)
    @FieldValueFill(ValueStrategy.SNOWFLAKE)
    private String roleId;

    /**
     * 角色码
     */
    private String roleCode;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 角色介绍
     */
    private String intro;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 租户编号
     */
    private String tenantId;

    /**
     * 应用 ID
     */
    private String appId;

}