package cn.youngkbt.uac.sys.model.dto;

import cn.youngkbt.uac.sys.model.po.SysLoginLog;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serializable;

/**
 * 操作日志记录
 * @TableName t_sys_login_log
 */
@Data
@AutoMapper(target = SysLoginLog.class, reverseConvertGenerate = false)
public class SysLoginLogDTO implements Serializable {
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
     * 状态（0 异常 1 正常 ）
     */
    private Integer status;

    /**
     * 租户编号
     */
    private String tenantId;

    private static final long serialVersionUID = 1L;
}