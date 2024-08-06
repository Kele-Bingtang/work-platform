package cn.youngkbt.file.system.model.dto;

import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2024/8/6 21:27:05
 * @note
 */
@Data
public class FileOperaLogDTO {
    /**
     * 日志 ID
     */
    private String operaId;

    /**
     * 应用系统标识
     */
    private String appId;

    /**
     * 附件唯一标识
     */
    private String fileKey;

    /**
     * 操作类型（0 上传 1 下载）
     */
    private String operateType;

    /**
     * 操作用户
     */
    private String operateUser;

    /**
     * 主机地址
     */
    private String operaIp;

    /**
     * 操作地点
     */
    private String operaLocation;

    /**
     * 消耗时间
     */
    private Integer costTime;

    /**
     * 备注
     */
    private String remark;
}
