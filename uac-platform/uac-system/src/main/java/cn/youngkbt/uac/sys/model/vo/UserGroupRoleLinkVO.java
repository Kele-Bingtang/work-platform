package cn.youngkbt.uac.sys.model.vo;

import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2023/12/28 0:03
 * @note
 */
@Data
public class UserGroupRoleLinkVO {
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
