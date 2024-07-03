package cn.youngkbt.ag.system.model.dto;

import cn.youngkbt.ag.system.model.po.ResponseConfig;
import cn.youngkbt.core.validate.RestGroup;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

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
     * 响应配置 ID
     */
    private String responseId;

    /**
     * 响应格式
     */
    private String responseJson;

    /**
     * 如果是一笔数据，是否以对象/数组形式返回（0 对象 1 数组），如果是多多笔数据，一定是以数组返回
     */
    private Integer responseArray;

    /**
     * 项目 ID
     */
    @NotBlank(message = "项目 ID 不能为空", groups = {RestGroup.AddGroup.class})
    private String projectId;

    /**
     * 服务 ID
     */
    @NotBlank(message = "服务 ID 不能为空", groups = {RestGroup.QueryGroup.class, RestGroup.AddGroup.class})
    private Long serviceId;
}
