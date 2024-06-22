package cn.youngkbt.uac.system.model.vo;

import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2023/12/16 0:44
 * @note
 */
@Data
public class RoleDeptLinkVO {
    /**
     * ID
     */
    private Long id;

    /**
     * 角色 ID
     */
    private String roleId;

    /**
     * 部门 ID
     */
    private String deptId;

    /**
     * 应用 ID
     */
    private String appId;
}
