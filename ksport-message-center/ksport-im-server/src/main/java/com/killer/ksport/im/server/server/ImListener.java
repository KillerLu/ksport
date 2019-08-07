package com.killer.ksport.im.server.server;

import com.killer.ksport.im.server.session.SessionManager;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author ：Killer
 * @date ：Created in 19-8-6 下午5:45
 * @description：${description}
 * @modified By：
 * @version: version
 */
@Component
public class ImListener {

    @RabbitHandler
    @RabbitListener(queues = "immediate_queue_test1")
    public void immediateProcess(String message) {
        ChannelGroup channelGroup = SessionManager.get(3l);
        if (channelGroup != null) {
            channelGroup.writeAndFlush(new TextWebSocketFrame(message));
        }
    }
}
