package cn.youngkbt.uac.sys.model.dto;

import cn.youngkbt.core.validate.RestGroup;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Kele-Bingtang
 * @date 2024/3/13 0:19
 * @note
 */
@Data
@AutoMapper(target = SysUserGroupDTO.class, reverseConvertGenerate = false)
public class SysUserGroupDTO {

    @NotNull(message = "id 不能为空", groups = { RestGroup.EditGroup.class })
    private Long id;
    
    /**
     * 用户组 ID
     */
    private String groupId;

    /**
     * 用户组名
     */
    private String groupName;

    /**
     * 用户组描述
     */
    private String intro;

    /**
     * 应用 ID
     */
    private String appId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
