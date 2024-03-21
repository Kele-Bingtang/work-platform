package cn.youngkbt.uac.sys.model.vo.link;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Kele-Bingtang
 * @date 2024/3/19 22:20
 * @note
 */
@Data
public class UserLinkInfoVO {

    /**
     * 用户 ID
     */
    private String userId;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 关联 ID
     */
    private Long linkId;

    /**
     * 生效时间
     */
    private LocalDate validFrom;

    /**
     * 失效时间
     */
    private LocalDate expireOn;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 应用 ID
     */
    private String appId;
}
