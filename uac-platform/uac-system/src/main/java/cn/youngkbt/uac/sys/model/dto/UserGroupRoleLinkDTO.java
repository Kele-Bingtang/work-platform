package cn.youngkbt.uac.sys.model.dto;

import cn.youngkbt.uac.sys.model.po.UserGroupRoleLink;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Kele-Bingtang
 * @date 2023/12/28 0:03
 * @note
 */
@Data
@AutoMapper(target = UserGroupRoleLink.class, reverseConvertGenerate = false)
public class UserGroupRoleLinkDTO {
    /**
     * 用户组 ID
     */
    private String userGroupId;

    /**
     * 角色 ID
     */
    private String roleId;

    /**
     * 生效时间
     */
    private LocalDate validFrom;

    /**
     * 失效时间
     */
    private LocalDate expireOn;

    /**
     * 应用 ID
     */
    private String appId;
}
