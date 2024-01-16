package cn.youngkbt.uac.sys.model.dto;

import cn.youngkbt.uac.sys.model.po.RoleGroupMenuLink;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2023/12/27 23:44
 * @note
 */
@Data
@AutoMapper(target = RoleGroupMenuLink.class, reverseConvertGenerate = false)
public class RoleGroupMenuLinkDto {
    /**
     * 角色组 ID
     */
    private String roleGroupId;

    /**
     * 菜单 ID
     */
    private String menuId;

    /**
     * 应用 ID
     */
    private String appId;
}
