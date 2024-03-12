package cn.youngkbt.uac.sys.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Kele-Bingtang
 * @date 2023-24-12 00:24:19
 * @note 岗位信息
*/
@Data
public class SysPostVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * id
     */
    private Long id;
    /**
     * 岗位 ID
     */
    private String postId;

    /**
     * 岗位编码
     */
    private String postCode;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 岗位用户数量
     */
    private Integer userCount;

    /**
     * 岗位介绍
     */
    private String intro;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}