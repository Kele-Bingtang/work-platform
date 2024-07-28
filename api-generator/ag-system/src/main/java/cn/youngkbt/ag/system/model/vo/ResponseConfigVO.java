package cn.youngkbt.ag.system.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.Data;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024/6/26 01:03:58
 * @note
 */
@Data
public class ResponseConfigVO {
    /**
     * 主键
     */
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
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private List<String> jsonCol;

    /**
     * 目录 ID
     */
    private String categoryId;

    /**
     * 服务 ID
     */
    private String serviceId;

    /**
     * 项目 ID
     */
    private String projectId;

    /**
     * 团队 ID
     */
    private String teamId;
}
