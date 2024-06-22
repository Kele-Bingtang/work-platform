package cn.youngkbt.uac.system.model.dto.link;

import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2024/4/2 23:49
 * @note
 */
@Data
public class UserGroupLinkInfoDTO {
    /**
     * 用户组名
     */
    private String userGroupName;

    /**
     * 负责人

     */
    private String owner;
}
