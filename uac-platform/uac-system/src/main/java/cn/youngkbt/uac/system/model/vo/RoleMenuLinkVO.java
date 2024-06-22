package cn.youngkbt.uac.system.model.vo;

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
     * 菜单 ID
     */
    private String menuId;

    /**
     * 菜单名
     */
    private String menuName;
}
