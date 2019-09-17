package com.killer.ksport.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @author ：Killer
 * @date ：Created in 19-6-24 下午2:57
 * @description：认证服务
 * @modified By：
 * @version: version
 */
@SpringBootApplication(scanBasePackages={"com.killer.ksport.*"})
@EnableFeignClients(basePackages={"com.killer.ksport.*"})
@EnableDiscoveryClient
public class KsportMessageApplication {

    public static void main(String[] args) {
        SpringApplication.run(KsportMessageApplication.class, args);
    }
}
