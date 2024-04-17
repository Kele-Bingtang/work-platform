package cn.youngkbt.uac.sys.model.dto;

import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.uac.sys.model.po.RoleDeptLink;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Kele-Bingtang
 * @date 2023/12/16 0:45
 * @note
 */
@Data
@AutoMapper(target = RoleDeptLink.class, reverseConvertGenerate = false)
public class RoleDeptLinkDTO {
    @NotBlank(message = "id 不能为空", groups = {RestGroup.EditGroup.class, RestGroup.DeleteGroup.class})
    private Long id;
    
    @NotBlank(message = "角色 ID 不能为空", groups = {RestGroup.AddGroup.class})
    private String roleId;

    @NotBlank(message = "部门 ID 不能为空", groups = {RestGroup.AddGroup.class})
    private String deptId;

    /**
     * 生效时间
     */
    @NotNull(message = "生效时间不能为空", groups = {RestGroup.AddGroup.class, RestGroup.EditGroup.class})
    private LocalDate validFrom;

    /**
     * 失效时间
     */
    @NotNull(message = "过期时间不能为空", groups = {RestGroup.AddGroup.class, RestGroup.EditGroup.class})
    private LocalDate expireOn;

    @NotBlank(message = "应用 ID 不能为空", groups = {RestGroup.QueryGroup.class, RestGroup.AddGroup.class})
    private String appId;
}
