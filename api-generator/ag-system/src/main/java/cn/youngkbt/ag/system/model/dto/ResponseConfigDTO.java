package cn.youngkbt.ag.system.model.dto;

import cn.youngkbt.ag.system.model.po.ResponseConfig;
import cn.youngkbt.core.validate.RestGroup;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024/6/26 01:04:43
 * @note
 */
@Data
@AutoMapper(target = ResponseConfig.class, reverseConvertGenerate = false)
public class ResponseConfigDTO {
    /**
     * 主键
     */
    @NotNull(message = "id 不能为空", groups = {RestGroup.EditGroup.class, RestGroup.DeleteGroup.class})
    private Long id;

    /**
     * 响应码
     */
    private String code;
    /**
     * 父级响应 ID
     */
    private String parentId;

    /**
     * 字段名
     */
    private String description;
    /**
     * 响应码描述
     */
    private List<String> jsonCol;

    /**
     * 目录 ID
     */
    @NotBlank(message = "目录 ID 不能为空", groups = {RestGroup.AddGroup.class})
    private String categoryId;

    /**
     * 服务 ID
     */
    @NotBlank(message = "服务 ID 不能为空", groups = {RestGroup.QueryGroup.class, RestGroup.AddGroup.class})
    private String serviceId;

    /**
     * 项目 ID
     */
    @NotBlank(message = "项目 ID 不能为空", groups = {RestGroup.AddGroup.class})
    private String projectId;

    /**
     * 团队 ID
     */
    @NotBlank(message = "团队 ID 不能为空", groups = {RestGroup.AddGroup.class})
    private String teamId;
}
