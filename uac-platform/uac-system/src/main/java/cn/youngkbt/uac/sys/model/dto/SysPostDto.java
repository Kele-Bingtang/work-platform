package cn.youngkbt.uac.sys.model.dto;

import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.uac.sys.model.po.SysPost;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2023-24-12 00:24:19
 * @note 岗位信息
*/
@Data
@AutoMapper(target = SysPost.class)
public class SysPostDto {
    /**
     * id
     */
    @NotNull(message = "id 不能为空", groups = { RestGroup.EditGroup.class })
    public Long id;
    /**
     * 岗位 ID
     */
    private String postId;

    /**
     * 岗位编码
     */
    @NotBlank(message = "岗位编码不能为空")
    @Size(min = 0, max = 64, message = "岗位编码长度不能超过 {max} 个字符")
    private String postCode;

    /**
     * 岗位名称
     */
    @NotBlank(message = "岗位名称不能为空")
    @Size(min = 0, max = 50, message = "岗位名称长度不能超过 {max} 个字符")
    private String postName;

    /**
     * 显示顺序
     */
    @NotNull(message = "显示顺序不能为空")
    private Integer orderNum;

    /**
     * 岗位介绍
     */
    private String intro;

    /**
     * 状态（0 异用 1 正常）
     */
    private Integer status;
}