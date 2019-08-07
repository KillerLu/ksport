package com.killer.ksport.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ：Killer
 * @date ：Created in 19-8-6 下午5:27
 * @description：${description}
 * @modified By：
 * @version: version
 */
@SpringBootApplication(scanBasePackages={"com.killer.ksport.*"})
public class KsportMqServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(KsportMqServerApplication.class, args);
    }

}
