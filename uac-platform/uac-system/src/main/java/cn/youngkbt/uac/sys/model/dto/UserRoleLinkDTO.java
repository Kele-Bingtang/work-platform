package cn.youngkbt.uac.sys.model.dto;

import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.uac.sys.model.po.UserRoleLink;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Kele-Bingtang
 * @date 2023/12/28 0:15
 * @note
 */
@Data
@AutoMapper(target = UserRoleLink.class, reverseConvertGenerate = false)
public class UserRoleLinkDTO {
    
    @NotNull(message = "id 不能为空", groups = {RestGroup.EditGroup.class})
    private Long id;

    /**
     * 用户 ID
     */
    private String userId;

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
