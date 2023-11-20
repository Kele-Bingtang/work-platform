package cn.youngkbt.uac.sys.model.po;

import cn.youngkbt.mp.base.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Kele-Bingtang
 * @date 2023-31-12 00:31:18
 * @note 用户关联角色组
*/
@TableName("t_user_role_group_link")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRoleGroupLink extends BaseDO {
    /**
     * 用户 ID
     */
    private String userId;

    /**
     * 角色组 ID
     */
    private String roleGroupId;

    /**
     * 应用 ID
     */
    private String appId;

}