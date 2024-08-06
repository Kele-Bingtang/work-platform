package cn.youngkbt.file.system.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Kele-Bingtang
 * @date 2024/8/6 21:27:21
 * @note
 */
@Data
public class FileExceptionLogVO {
    /**
     * IP
     */
    private String ip;

    /**
     * 链接
     */
    private String requestUrl;

    /**
     * 操作用户
     */
    private String operateUser;

    /**
     * 备注
     */
    private String remark;

    /**
     * 异常内容
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
