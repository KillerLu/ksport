package com.killer.ksport.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @author ：Killer
 * @date ：Created in 19-6-24 下午2:57
 * @description：认证服务
 * @modified By：
 * @version: version
 */
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class},scanBasePackages={"com.killer.ksport.*"})
@EnableWebSecurity
public class KsportAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(KsportAuthApplication.class, args);
    }
}
