package cn.youngkbt.uac.sys.model.po;

import cn.youngkbt.mp.base.BaseDO;
import cn.youngkbt.uac.sys.model.vo.UserGroupRoleGroupLinkVo;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Kele-Bingtang
 * @date 2023-30-12 00:30:26
 * @note 用户组关联角色组
*/
@TableName("t_user_group_role_group_link")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = UserGroupRoleGroupLinkVo.class, reverseConvertGenerate = false)
public class UserGroupRoleGroupLink extends BaseDO {
    /**
     * 用户组 ID
     */
    private String userGroupId;

    /**
     * 角色组 ID
     */
    private String roleGroupId;

    /**
     * 应用 ID
     */
    private String appId;

}