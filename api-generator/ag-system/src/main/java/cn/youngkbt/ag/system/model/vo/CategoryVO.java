package cn.youngkbt.ag.system.model.vo;

import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2024/6/24 23:51:21
 * @note
 */
@Data
public class CategoryVO {
    /**
     * id
     */
    private Integer id;
    /**
     * 分类 ID
     */
    private String categoryId;

    /**
     * 目录编码
     */
    private String categoryCode;

    /**
     * 目录名称
     */
    private String categoryName;

    /**
     * 是否是主目录（0 不是 1 是）
     */
    private Integer isMain;

    /**
     * 项目 id
     */
    private String projectId;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 团队 ID
     */
    private String teamId;
}
