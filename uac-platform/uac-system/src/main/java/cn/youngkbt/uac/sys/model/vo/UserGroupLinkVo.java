package cn.youngkbt.uac.sys.model.vo;

import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2023/12/27 23:51
 * @note
 */
@Data
public class UserGroupLinkVo {
    /**
     * 用户 ID
     */
    private String userId;

    /**
     * 用户组 UID
     */
    private String userGroupId;
}
