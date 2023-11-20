package cn.youngkbt.uac.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

/**
 * @author Kele-Bingtang
 * @date 2023/11/12 2:35
 * @note
 */
@SpringBootApplication(scanBasePackages = "cn.youngkbt")
public class UacMainApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(UacMainApplication.class);
        application.setApplicationStartup(new BufferingApplicationStartup(2048));
        application.run(args);
        System.out.println("(♥◠‿◠)ﾉﾞ Work UAC 平台启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
