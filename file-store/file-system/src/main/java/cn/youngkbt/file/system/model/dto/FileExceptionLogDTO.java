package cn.youngkbt.file.system.model.dto;

import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2024/8/6 21:26:32
 * @note
 */
@Data
public class FileExceptionLogDTO {
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
}
