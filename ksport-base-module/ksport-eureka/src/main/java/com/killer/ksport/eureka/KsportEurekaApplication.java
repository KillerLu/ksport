package com.killer.ksport.eureka;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author ：Killer
 * @date ：Created in 19-6-21 下午3:34
 * @description：${description}
 * @modified By：
 * @version: version
 */
@SpringBootApplication
@EnableEurekaServer
public class KsportEurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(KsportEurekaApplication.class, args);
    }
}
