package cn.youngkbt.ag.system.model.dto;

import cn.youngkbt.ag.system.model.po.Team;
import cn.youngkbt.core.validate.RestGroup;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2024/6/22 15:11:01
 * @note
 */
@Data
@AutoMapper(target = Team.class, reverseConvertGenerate = false)
public class TeamDTO {
    /**
     * 主键
     */
    @NotNull(message = "id 不能为空", groups = {RestGroup.EditGroup.class, RestGroup.DeleteGroup.class})
    private Long id;

    /**
     * 团队 ID
     */
    private String teamId;

    /**
     * 团队名字
     */
    @NotBlank(message = "团队名字不能为空", groups = {RestGroup.AddGroup.class})
    private String teamName;

    /**
     * 团队介绍
     */
    private String description;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 状态（0 异常 1 正常 ）
     */
    private Integer status;
}
