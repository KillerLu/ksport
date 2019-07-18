package com.killer.ksport.auth;

import com.killer.ksport.common.core.config.MybatisPlusConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @author ：Killer
 * @date ：Created in 19-6-24 下午2:57
 * @description：认证服务
 * @modified By：
 * @version: version
 */
@SpringBootApplication(scanBasePackages={"com.killer.ksport.*"})
@EnableWebSecurity
@EnableFeignClients(basePackages={"com.killer.ksport.*"})
@EnableDiscoveryClient
public class KsportAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(KsportAuthApplication.class, args);
    }
}
