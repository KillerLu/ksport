package com.killer.ksport.im.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ：Killer
 * @date ：Created in 19-6-24 下午2:57
 * @description：im客户端
 * @modified By：
 * @version: version
 */
@SpringBootApplication(scanBasePackages={"com.killer.ksport.*"})
public class KsportImClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(KsportImClientApplication.class, args);
    }
}
