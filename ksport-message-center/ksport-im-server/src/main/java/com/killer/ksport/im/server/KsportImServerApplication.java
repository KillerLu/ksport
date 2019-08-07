package com.killer.ksport.im.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ：Killer
 * @date ：Created in 19-6-24 下午2:57
 * @description：认证服务
 * @modified By：
 * @version: version
 */
@SpringBootApplication(scanBasePackages={"com.killer.ksport.*"})
public class KsportImServerApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(KsportImServerApplication.class);
        app.setWebEnvironment(false);
        app.run(args);
    }
}
