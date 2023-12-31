package cn.youngkbt.uac.sys.model.po;

import cn.youngkbt.mp.annotation.FieldValueFill;
import cn.youngkbt.mp.annotation.ValueStrategy;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Kele-Bingtang
 * @date 2023-22-12 00:22:36
 * @note 操作日志记
*/
@TableName("t_sys_login_info")
@Data
@Builder
public class SysLoginLog implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 访问 ID
     */
    @TableField(fill = FieldFill.INSERT)
    @FieldValueFill(ValueStrategy.SNOWFLAKE)
    private String loginId;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 客户端
     */
    private String clientKey;

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

    /**
     * 租户编号
     */
    private String tenantId;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}