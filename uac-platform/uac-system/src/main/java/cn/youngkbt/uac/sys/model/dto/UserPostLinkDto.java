package cn.youngkbt.uac.sys.model.dto;

import cn.youngkbt.uac.sys.model.po.UserPostLink;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2023/12/28 0:08
 * @note
 */
@Data
@AutoMapper(target = UserPostLink.class, reverseConvertGenerate = false)
public class UserPostLinkDto {
    /**
     * 用户 ID
     */
    private String userId;

    /**
     * 岗位 ID
     */
    private String postId;

    /**
     * 租户编号
     */
    private String tenantId;
}
