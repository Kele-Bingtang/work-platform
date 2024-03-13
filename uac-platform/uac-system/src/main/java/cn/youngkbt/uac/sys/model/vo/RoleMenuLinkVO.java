package cn.youngkbt.uac.sys.model.vo;

import lombok.Data;

import java.time.LocalDate;

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
     * 生效时间
     */
    private LocalDate validFrom;

    /**
     * 失效时间
     */
    private LocalDate expireOn;
    /**
     * 菜单名
     */
    private String menuName;
}
