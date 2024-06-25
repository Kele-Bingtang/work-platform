package cn.youngkbt.ag.system.model.vo;

import lombok.Data;

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
    private String projectId;

    /**
     * 服务 ID
     */
    private Long serviceId;
}
