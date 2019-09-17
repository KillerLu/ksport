package com.killer.ksport.message.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author ：Killer
 * @date ：Created in 19-9-16 上午10:15
 * @description：${description}
 * @modified By：
 * @version: version
 */
@Configuration
public class RestTempletConfig {

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
