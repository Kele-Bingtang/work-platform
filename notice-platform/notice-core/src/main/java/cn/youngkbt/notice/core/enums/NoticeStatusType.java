package cn.youngkbt.notice.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Kele-Bingtang
 * @date 2024/9/28 14:31:38
 * @note
 */
@Getter
@AllArgsConstructor
public enum NoticeStatusType {
    
    NEW("New"),
    READY("Ready"),
    SENDING("Sending"),
    COMPLETED("Completed"),
    FAILED("Failed"),
    HOLD("Hold"),
    ;
    
    private final String status;
}
