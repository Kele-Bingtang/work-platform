package cn.youngkbt.uac.sys.model.vo;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author Kele-Bingtang
 * @date 2023/12/27 23:51
 * @note
 */
@Data
public class UserGroupLinkVO {
    /**
     * 用户 ID
     */
    private String userId;

    /**
     * 用户组 UID
     */
    private String userGroupId;

    /**
     * 生效时间
     */
    private LocalDate validFrom;

    /**
     * 失效时间
     */
    private LocalDate expireOn;

    /**
     * 应用 ID
     */
    private String appId;
}
