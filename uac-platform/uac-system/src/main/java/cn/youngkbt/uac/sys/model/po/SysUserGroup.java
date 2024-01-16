package cn.youngkbt.uac.sys.model.po;

import cn.youngkbt.mp.annotation.FieldValueFill;
import cn.youngkbt.mp.annotation.ValueStrategy;
import cn.youngkbt.mp.base.BaseDO;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Kele-Bingtang
 * @date 2023-27-12 00:27:26
 * @note 用户组信息
*/
@TableName("t_sys_user_group")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserGroup extends BaseDO {
    /**
     * 用户组 ID
     */
    @TableField(fill = FieldFill.INSERT)
    @FieldValueFill(ValueStrategy.SNOWFLAKE)
    private String groupId;

    /**
     * 用户组名
     */
    private String groupName;

    /**
     * 用户组描述
     */
    private String intro;

    /**
     * 租户编号
     */
    private String tenantId;

}