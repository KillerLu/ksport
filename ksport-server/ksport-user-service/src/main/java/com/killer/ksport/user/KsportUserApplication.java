package com.killer.ksport.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
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
@EnableDiscoveryClient
@EnableFeignClients(basePackages={"com.killer.ksport.*"})
public class KsportUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(KsportUserApplication.class, args);
    }
}
