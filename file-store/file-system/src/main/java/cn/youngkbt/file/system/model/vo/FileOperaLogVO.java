package cn.youngkbt.file.system.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Kele-Bingtang
 * @date 2024/8/6 21:28:27
 * @note
 */
@Data
public class FileOperaLogVO {
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

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
