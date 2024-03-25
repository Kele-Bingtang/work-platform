package cn.youngkbt.uac.sys.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Kele-Bingtang
 * @date 2023-24-12 00:24:45
 * @note 应用角色信息
 */
@Data
public class SysRoleVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * id
     */
    private Long id;
    
    /**
     * 角色 ID
     */
    private String roleId;

    /**
     * 角色码
     */
    private String roleCode;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 角色介绍
     */
    private String intro;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 应用 ID
     */
    private String appId;

}