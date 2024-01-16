package cn.youngkbt.uac.sys.model.po;

import cn.youngkbt.mp.annotation.FieldValueFill;
import cn.youngkbt.mp.annotation.ValueStrategy;
import cn.youngkbt.mp.base.BaseDO;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Kele-Bingtang
 * @date 2023-25-12 00:25:13
 * @note 角色组信息
*/
@TableName("t_sys_role_group")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleGroup extends BaseDO {
    /**
     * 角色组 ID
     */
    @TableField(fill = FieldFill.INSERT)
    @FieldValueFill(ValueStrategy.SNOWFLAKE)
    private String groupId;

    /**
     * 角色组名
     */
    private String groupName;

    /**
     * 角色组介绍
     */
    private String intro;

    /**
     * 应用 ID
     */
    private String appId;

}