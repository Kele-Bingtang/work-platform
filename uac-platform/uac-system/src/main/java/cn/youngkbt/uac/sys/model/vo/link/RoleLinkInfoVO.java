package cn.youngkbt.uac.sys.model.vo.link;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Kele-Bingtang
 * @date 2024/3/21 22:40
 * @note
 */
@Data
public class RoleLinkInfoVO {
    /**
     * 角色 ID
     */
    private String roleId;

    /**
     * 角色名
     */
    private String roleName;
    
    /**
     * 角色码
     */
    private String roleCode;

    /**
     * 关联 ID
     */
    private Long linkId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 应用 ID
     */
    private String appId;
}
