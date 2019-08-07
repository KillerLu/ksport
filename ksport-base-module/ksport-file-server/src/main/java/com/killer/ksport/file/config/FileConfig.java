package com.killer.ksport.file.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.MultipartConfigElement;

/**
 * @author ：Killer
 * @date ：Created in 19-7-19 下午2:52
 * @description：${description}
 * @modified By：
 * @version: version
 */
@Configuration
public class FileConfig {

    /**
     * 文件上传配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize("300MB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("500MB");
        return factory.createMultipartConfig();
    }

}
