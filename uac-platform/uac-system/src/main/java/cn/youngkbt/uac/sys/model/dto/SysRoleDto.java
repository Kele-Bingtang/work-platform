package cn.youngkbt.uac.sys.model.dto;

import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.uac.sys.model.po.SysRole;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2023-24-12 00:24:45
 * @note 应用角色信息
 */
@Data
@AutoMapper(target = SysRole.class)
public class SysRoleDto {
    /**
     * id
     */
    @NotNull(message = "id 不能为空", groups = { RestGroup.EditGroup.class })
    public Long id;
    /**
     * 角色 ID
     */
    private String roleId;

    /**
     * 角色码
     */
    @NotBlank(message = "角色码不能为空")
    @Size(min = 0, max = 30, message = "角色码长度不能超过 {max} 个字符")
    private String roleCode;

    /**
     * 角色名
     */
    @NotBlank(message = "角色名称不能为空")
    @Size(min = 0, max = 30, message = "角色名称长度不能超过{max}个字符")
    private String roleName;

    /**
     * 角色介绍
     */
    private String intro;

    /**
     * 显示顺序
     */
    @NotNull(message = "显示顺序不能为空")
    private Integer orderNum;

    /**
     * 应用 ID
     */
    private String appId;
    
    /**
     * 状态（0 异用 1 正常）
     */
    private Integer status;
    
}