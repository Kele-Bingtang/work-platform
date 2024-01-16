package cn.youngkbt.uac.sys.model.dto;

import cn.youngkbt.uac.sys.model.po.UserRoleGroupLink;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2023/12/28 0:11
 * @note
 */
@Data
@AutoMapper(target = UserRoleGroupLink.class, reverseConvertGenerate = false)
public class UserRoleGroupLinkDto {
    /**
     * 用户 ID
     */
    private String userId;

    /**
     * 角色组 ID
     */
    private String roleGroupId;

    /**
     * 应用 ID
     */
    private String appId;
}
