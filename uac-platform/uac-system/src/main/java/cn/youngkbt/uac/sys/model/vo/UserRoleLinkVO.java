package cn.youngkbt.uac.sys.model.vo;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author Kele-Bingtang
 * @date 2023/12/28 0:14
 * @note
 */
@Data
public class UserRoleLinkVO {
    /**
     * 用户 ID
     */
    private String userId;

    /**
     * 角色 ID
     */
    private String roleId;

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
