package cn.youngkbt.uac.sys.model.vo;

import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2023/12/28 0:11
 * @note
 */
@Data
public class UserRoleGroupLinkVo {
    /**
     * 用户 ID
     */
    private String userId;

    /**
     * 角色组 ID
     */
    private String roleGroupId;
}
