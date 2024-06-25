package cn.youngkbt.ag.system.model.dto;

import cn.youngkbt.ag.system.model.po.Project;
import cn.youngkbt.core.validate.RestGroup;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2024/6/24 22:39:58
 * @note
 */
@Data
@AutoMapper(target = Project.class, reverseConvertGenerate = false)
public class ProjectDTO {
    /**
     * 主键
     */
    @NotNull(message = "id 不能为空", groups = {RestGroup.EditGroup.class})
    private Integer id;

    /**
     * 项目 ID
     */
    private String projectId;

    /**
     * 项目名
     */
    @NotBlank(message = "项目名不能为空", groups = {RestGroup.AddGroup.class, RestGroup.EditGroup.class})
    private String projectName;

    /**
     * 接口基础路径
     */
    @NotBlank(message = "基础接口路径不能为空", groups = {RestGroup.AddGroup.class, RestGroup.EditGroup.class})
    private String baseUrl;

    /**
     * 项目描述
     */
    private String description;

    /**
     * 项目密钥，唯一
     */
    private String secretKey;

    /**
     * 数据库名称
     */
    private String databaseName;

    /**
     * 0 项目创建者 1 项目加入者
     */
    private Integer belongType;

    /**
     * 状态（0 禁用 1 启用）
     */
    private Integer status;

    /**
     * 团队 ID
     */
    @NotBlank(message = "团队 ID 不能为空", groups = {RestGroup.QueryGroup.class, RestGroup.AddGroup.class, RestGroup.EditGroup.class, RestGroup.DeleteGroup.class})
    private String teamId;
}
