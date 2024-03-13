package cn.youngkbt.uac.sys.model.dto;

import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.uac.sys.model.po.RoleMenuLink;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Kele-Bingtang
 * @date 2023/12/16 0:17
 * @note
 */
@Data
@AutoMapper(target = RoleMenuLink.class, reverseConvertGenerate = false)
public class RoleMenuLinkDTO {
    @NotBlank(message = "id 不能为空", groups = {RestGroup.EditGroup.class, RestGroup.DeleteGroup.class})
    private Long id;
    
    @NotBlank(message = "角色 ID 不能为空", groups = {RestGroup.AddGroup.class})
    private String roleId;

    @NotBlank(message = "菜单 ID 不能为空", groups = {RestGroup.AddGroup.class})
    private String menuId;

    /**
     * 生效时间
     */
    private LocalDate validFrom;

    /**
     * 失效时间
     */
    private LocalDate expireOn;

    @NotBlank(message = "应用 ID 不能为空", groups = {RestGroup.QueryGroup.class, RestGroup.AddGroup.class})
    private String appId;
}
