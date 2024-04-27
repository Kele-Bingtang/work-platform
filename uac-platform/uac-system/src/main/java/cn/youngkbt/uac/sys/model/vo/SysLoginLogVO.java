package cn.youngkbt.uac.sys.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 操作日志记录
 * @TableName t_sys_login_log
 */
@Data
public class SysLoginLogVO implements Serializable {
    /**
     * 主键
     */
    private Long id;
    
    /**
     * 登录 ID
     */
    private String loginId;
    
    /**
     * 用户账号
     */
    private String username;

    /**
     * 客户端名
     */
    private String clientName;

    /**
     * 登录 IP 地址
     */
    private String loginIp;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 提示消息
     */
    private String msg;

    /**
     * 状态（0 异常 1 正常 ）
     */
    private Integer status;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    private static final long serialVersionUID = 1L;
}