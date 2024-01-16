package cn.youngkbt.uac.sys.model.dto;

import cn.youngkbt.uac.sys.model.po.UserGroupLink;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2023/12/27 23:50
 * @note
 */
@Data
@AutoMapper(target = UserGroupLink.class, reverseConvertGenerate = false)
public class UserGroupLinkDto {
    /**
     * 用户 ID
     */
    private String userId;

    /**
     * 用户组 UID
     */
    private String userGroupId;

    /**
     * 租户编号
     */
    private String tenantId;
}
