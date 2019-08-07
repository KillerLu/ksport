package com.killer.ksport.mq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author ：Killer
 * @date ：Created in 19-8-6 下午5:36
 * @description：${description}
 * @modified By：
 * @version: version
 */
@Component
public class Sender {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void send() {
        String message = "message" + new Date();
        System.out.println("Sender  " + message);
        rabbitTemplate.convertAndSend("immediate_exchange_test1", "immediate_routing_key_test1", message);
    }
}
