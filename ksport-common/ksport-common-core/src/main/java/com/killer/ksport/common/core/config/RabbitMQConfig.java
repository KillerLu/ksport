package com.killer.ksport.common.core.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：Killer
 * @date ：Created in 19-8-6 下午5:33
 * @description：${description}
 * @modified By：
 * @version: version
 */
@Configuration
public class RabbitMQConfig {


    public static final String TRANSACTION_MSG_QUEUE = "transactionMsg";

    // 创建一个立即消费队列
    @Bean(name = "transactionMsgQueue")
    public Queue transactionMsgQueue() {
        // 第一个参数是创建的queue的名字，第二个参数是是否支持持久化
        return new Queue(TRANSACTION_MSG_QUEUE, true);
    }

    @Bean(name = "transactionExchange")
    public DirectExchange transactionMsgExchange() {
        // 一共有三种构造方法，可以只传exchange的名字， 第二种，可以传exchange名字，是否支持持久化，是否可以自动删除，
        //第三种在第二种参数上可以增加Map，Map中可以存放自定义exchange中的参数
        return new DirectExchange(TRANSACTION_MSG_QUEUE, true, false);
    }

    @Bean(name = "transactionBinding")
    //把立即消费的队列和立即消费的exchange绑定在一起
    public Binding transactionBinding() {
        return BindingBuilder.bind(transactionMsgQueue()).to(transactionMsgExchange()).with(TRANSACTION_MSG_QUEUE);
    }

}
