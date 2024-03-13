package cn.youngkbt.uac.sys.model.dto.link;

import cn.youngkbt.uac.sys.model.po.UserGroupLink;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024/3/13 23:28
 * @note 
 */
@Data
@AutoMapper(target = UserGroupLink.class, reverseConvertGenerate = false)
public class UserGroupLinkUserDTO {
    /**
     * 用户 ID
     */
    private List<String> userIds;

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
}
