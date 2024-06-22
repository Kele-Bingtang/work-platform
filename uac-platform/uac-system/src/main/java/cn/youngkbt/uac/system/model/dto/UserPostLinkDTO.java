package cn.youngkbt.uac.system.model.dto;

import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.uac.system.model.po.UserPostLink;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2023/12/28 0:08
 * @note
 */
@Data
@AutoMapper(target = UserPostLink.class, reverseConvertGenerate = false)
public class UserPostLinkDTO {
    /**
     * 主键
     */
    @NotNull(message = "id 不能为空", groups = {RestGroup.EditGroup.class, RestGroup.DeleteGroup.class})
    private String id;
    /**
     * 用户 ID
     */
    private String userId;

    /**
     * 岗位 ID
     */
    private String postId;
}
