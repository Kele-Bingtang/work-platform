package cn.youngkbt.monitor.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Kele-Bingtang
 * @date 2024/4/10 下午11:08
 * @note
 */
@SpringBootApplication
@EnableAdminServer
public class MonitorAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitorAdminApplication.class, args);
        System.out.println("Admin 监控启动成功");
    }
}
