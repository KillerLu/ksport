package com.killer.ksport.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author ：Killer
 * @date ：Created in 19-8-7 下午2:18
 * @description：${description}
 * @modified By：
 * @version: version
 */
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages={"com.killer.ksport.*"})
public class KsportZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(KsportZuulApplication.class, args);
    }
}
