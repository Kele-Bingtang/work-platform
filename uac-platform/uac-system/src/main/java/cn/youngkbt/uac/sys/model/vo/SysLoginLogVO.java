package cn.youngkbt.uac.sys.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志记录
 * @TableName t_sys_login_log
 */
@Data
public class SysLoginLogVO implements Serializable {
    /**
     * 访问 ID
     */
    private String loginId;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 客户端
     */
    private String appId;

    /**
     * 设备类型
     */
    private String deviceType;

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
     * 访问时间
     */
    private Date loginTime;

    private static final long serialVersionUID = 1L;
}