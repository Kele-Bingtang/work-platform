package cn.youngkbt.uac.sys.model.dto;

import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.uac.sys.model.po.SysDictType;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2023/12/4 16:29
 * @note
 */
@Data
@AutoMapper(target = SysDictType.class)
public class SysDictTypeDto {
    /**
     * id
     */
    @NotNull(message = "id 不能为空", groups = { RestGroup.EditGroup.class })
    public Long id;
    /**
     * 字典主键
     */
    private String dictId;
    /**
     * 字典类型
     */
    @NotBlank(message = "字典类型不能为空")
    @Size(min = 0, max = 100, message = "字典类型类型长度不能超过 {max} 个字符")
    @Pattern(regexp = "^[a-z][a-z0-9_]*$", message = "字典类型必须以字母开头，且只能为（小写字母，数字，下滑线）")
    private String dictCode;

    /**
     * 字典名称
     */
    @NotBlank(message = "字典名称不能为空")
    @Size(min = 0, max = 100, message = "字典类型名称长度不能超过 {max} 个字符")
    private String dictName;

    /**
     * 应用 ID
     */
    private String appId;
}
