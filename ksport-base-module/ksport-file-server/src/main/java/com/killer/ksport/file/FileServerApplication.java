package com.killer.ksport.file;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ：Killer
 * @date ：Created in 19-6-21 下午3:34
 * @description：${description}
 * @modified By：
 * @version: version
 */
@SpringBootApplication(scanBasePackages={"com.killer.ksport.*"})
public class FileServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileServerApplication.class, args);
    }
}
