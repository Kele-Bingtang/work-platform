package cn.youngkbt.uac.sys.model.vo;

import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2023/12/28 0:14
 * @note
 */
@Data
public class UserRoleLinkVo {
    /**
     * 用户 ID
     */
    private String userId;

    /**
     * 角色 ID
     */
    private String roleId;

}
