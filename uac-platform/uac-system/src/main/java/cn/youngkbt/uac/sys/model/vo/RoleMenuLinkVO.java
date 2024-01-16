package cn.youngkbt.uac.sys.model.vo;

import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2023/12/16 0:13
 * @note
 */
@Data
public class RoleMenuLinkVO {
    /**
     * ID
     */
    private Long id;
    /**
     * 角色 ID
     */
    private String roleId;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 菜单 ID
     */
    private String menuId;
    /**
     * 菜单名
     */
    private String menuName;
}
