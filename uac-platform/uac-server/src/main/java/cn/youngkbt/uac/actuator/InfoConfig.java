package cn.youngkbt.uac.actuator;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Kele-Bingtang
 * @date 2024/4/13 上午12:20
 * @note
 */
@Component
public class InfoConfig implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Object> infoMap = new LinkedHashMap<>();
        infoMap.put("Author", "Young Kbt");
        infoMap.put("Version", "1.0.0");
        infoMap.put("RunTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        infoMap.put("Copyright", "Copyright © 2023-2024 YoungKbt. All rights reserved.");
        builder.withDetails(infoMap);
    }
}