package cn.youngkbt.uac.system.model.dto.link;

import cn.youngkbt.core.validate.RestGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024/3/18 20:43
 * @note
 */
@Data
public class RoleLinkUserGroupDTO {
    /**
     * 主键 ID
     */
    @NotNull(message = "主键 ID 不能为空", groups = {RestGroup.EditGroup.class, RestGroup.DeleteGroup.class})
    private Long id;

    /**
     * 用户 ID
     */
    @NotBlank(message = "角色 ID 不能为空", groups = {RestGroup.AddGroup.class})
    private String roleId;

    /**
     * 用户组 ID
     */
    @NotNull(message = "用户组 ID 不能为空", groups = {RestGroup.AddGroup.class})
    private List<String> userGroupIds;

    /**
     * 应用 ID
     */
    @NotNull(message = "应用 ID 不能为空", groups = {RestGroup.AddGroup.class})
    private String appId;
}
