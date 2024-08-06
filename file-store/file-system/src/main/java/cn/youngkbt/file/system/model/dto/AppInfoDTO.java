package cn.youngkbt.file.system.model.dto;

import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2024/8/6 21:19:46
 * @note
 */
@Data
public class AppInfoDTO {
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
}
