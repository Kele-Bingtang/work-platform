package cn.youngkbt.ag.system.model.vo;

import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2024/6/24 22:36:18
 * @note
 */
@Data
public class ProjectVO {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 项目 ID
     */
    private String projectId;
    /**
     * 项目名
     */
    private String projectName;
    /**
     * 接口基础路径
     */
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
     * 团队 ID
     */
    private String teamId;
}
