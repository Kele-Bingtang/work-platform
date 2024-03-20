package cn.youngkbt.uac.sys.model.dto;

import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.uac.sys.model.po.UserGroupLink;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Kele-Bingtang
 * @date 2023/12/27 23:50
 * @note
 */
@Data
@AutoMapper(target = UserGroupLink.class, reverseConvertGenerate = false)
public class UserGroupLinkDTO {
    /**
     * 主键
     */
    @NotNull(message = "id 不能为空", groups = {RestGroup.EditGroup.class})
    private String id;
    
    /**
     * 用户 ID
     */
    private String userId;

    /**
     * 用户组 UID
     */
    private String userGroupId;

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
