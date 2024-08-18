package cn.youngkbt.notice.system.model.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024/8/18 23:40:01
 * @note
 */
@Data
public class NoticeInfoDTO {
    /**
     * 接收者
     */
    private String to;
    /**
     * 抄送者
     */
    private String cc;
    /**
     * 密送者
     */
    private String bcc;
    /**
     * 主题
     */
    private String subject;
    /**
     * 正文
     */
    private String content;
    /**
     * 优先级
     */
    private int priority;
    /**
     * 附件
     */
    private List<MultipartFile> fileList;
}
