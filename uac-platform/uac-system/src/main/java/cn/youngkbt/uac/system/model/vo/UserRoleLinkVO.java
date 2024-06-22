package cn.youngkbt.uac.system.model.vo;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author Kele-Bingtang
 * @date 2023/12/28 0:14
 * @note 目前接口是批量关联（多个 ids），该类是一对一关联，因此暂未使用
 * 批量关联请看 link 目录下的 VO 类
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
