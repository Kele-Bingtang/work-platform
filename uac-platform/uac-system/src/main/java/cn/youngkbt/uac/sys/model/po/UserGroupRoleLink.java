package cn.youngkbt.uac.sys.model.po;

import cn.youngkbt.mp.base.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Kele-Bingtang
 * @date 2023-30-12 00:30:42
 * @note 用户组关联角色
*/
@TableName("t_user_group_role_link")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserGroupRoleLink extends BaseDO {
    /**
     * 用户组 ID
     */
    private String userGroupId;

    /**
     * 角色 ID
     */
    private String roleId;

    /**
     * 应用 ID
     */
    private String appId;
}