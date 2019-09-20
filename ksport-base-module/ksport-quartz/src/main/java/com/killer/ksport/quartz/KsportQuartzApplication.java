package com.killer.ksport.quartz;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @author ：Killer
 * @date ：Created in 19-6-21 下午3:34
 * @description：${description}
 * @modified By：
 * @version: version
 */
@SpringBootApplication(scanBasePackages={"com.killer.ksport.*"})
@EnableFeignClients(basePackages={"com.killer.ksport.*"})
@EnableDiscoveryClient
public class KsportQuartzApplication {
    public static void main(String[] args) {
        SpringApplication.run(KsportQuartzApplication.class, args);
    }
}
