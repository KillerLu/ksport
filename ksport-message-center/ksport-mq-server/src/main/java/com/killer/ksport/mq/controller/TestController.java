package com.killer.ksport.mq.controller;

import com.killer.ksport.mq.producer.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：Killer
 * @date ：Created in 19-8-6 下午5:40
 * @description：${description}
 * @modified By：
 * @version: version
 */
@RestController
public class TestController {

    @Autowired
    private Sender sender;


    @RequestMapping("/send")
    public String testSend(String message){
        sender.send();
        return "ok";
    }
}
