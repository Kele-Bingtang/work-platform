package cn.youngkbt.uac.sys.model.dto;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2023/12/27 23:59
 * @note
 */
@Data
@AutoMapper(target = UserGroupRoleGroupLinkDto.class, reverseConvertGenerate = false)
public class UserGroupRoleGroupLinkDto {
    /**
     * 用户组 ID
     */
    private String userGroupId;

    /**
     * 角色组 ID
     */
    private String roleGroupId;

    /**
     * 应用 ID
     */
    private String appId;
}
