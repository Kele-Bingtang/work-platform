package cn.youngkbt.uac.sys.model.dto;

import cn.youngkbt.uac.sys.model.po.RoleGroupLink;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2023/12/27 23:39
 * @note
 */
@Data
@AutoMapper(target = RoleGroupLink.class, reverseConvertGenerate = false)
public class RoleGroupLinkDto {
    /**
     * 角色 ID
     */
    private String roleId;

    /**
     * 角色组 ID
     */
    private String roleGroupId;

    /**
     * 应用 ID
     */
    private String appId;
}
