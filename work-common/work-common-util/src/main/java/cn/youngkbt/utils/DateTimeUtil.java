package cn.youngkbt.utils;

import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Kele-Bingtang
 * @date 2024/4/15 23:19:12
 * @note
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class DateTimeUtil {
    
    public static String now() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
