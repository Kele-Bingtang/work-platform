package cn.youngkbt.uac.system.model.dto.link;

import cn.youngkbt.core.validate.RestGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024/3/13 23:28
 * @note 
 */
@Data
public class UserGroupLinkUserDTO {
    /**
     * 主键 ID
     */
    @NotNull(message = "主键 ID 不能为空", groups = {RestGroup.EditGroup.class, RestGroup.DeleteGroup.class})
    private Long id;

    /**
     * 用户 ID
     */
    @NotNull(message = "用户 ID 不能为空", groups = {RestGroup.AddGroup.class})
    private List<String> userIds;

    /**
     * 用户组 ID
     */
    @NotBlank(message = "用户组 ID 不能为空", groups = {RestGroup.AddGroup.class})
    private String userGroupId;

    /**
     * 生效时间
     */
    @NotNull(message = "生效时间不能为空", groups = {RestGroup.AddGroup.class})
    private LocalDate validFrom;

    /**
     * 过期时间
     */
    @NotNull(message = "过期时间不能为空", groups = {RestGroup.AddGroup.class})
    private LocalDate expireOn;

    /**
     * 应用 ID
     */
    @NotNull(message = "应用 ID 不能为空", groups = {RestGroup.AddGroup.class})
    private String appId;
}
