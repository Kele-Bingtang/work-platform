package cn.youngkbt.file.system.model.po;

import cn.youngkbt.mp.base.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * app 配置表
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="t_app_info")
@Data
public class AppInfo extends BaseDO {
    /**
     * 应用 ID
     */
    private String appId;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 负责人 ID
     */
    private String owner;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 失败短信提醒（0 不发送 1 发送）
     */
    private String failedSm;

    /**
     * 失败信息邮件通知（0 不发送 1 发送）
     */
    private String failedEm;

    /**
     * 备注
     */
    private String remark;

    @Serial
    private static final long serialVersionUID = 1L;
}