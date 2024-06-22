package cn.youngkbt.uac.system.model.dto.link;

import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2024/3/25 20:32
 * @note
 */
@Data
public class UserLinkInfoDTO {
    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;
}
