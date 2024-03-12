package cn.youngkbt.uac.sys.model.dto;

import cn.youngkbt.uac.sys.model.po.UserRoleLink;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2023/12/28 0:15
 * @note
 */
@Data
@AutoMapper(target = UserRoleLink.class, reverseConvertGenerate = false)
public class UserRoleLinkDTO {
    /**
     * 用户 ID
     */
    private String userId;

    /**
     * 角色 ID
     */
    private String roleId;

    /**
     * 应用 ID
     */
    private String appId;
}
