package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author ：Killer
 * @date ：Created in 19-6-24 上午10:01
 * @description：管理配置微服务
 * @modified By：
 * @version: version
 */
@SpringBootApplication
@EnableConfigServer
public class KsportConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(KsportConfigApplication.class, args);
    }
}
