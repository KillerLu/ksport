package com.killer.ksport.im.client.service.impl;

import com.killer.ksport.common.core.service.IRedisService;
import com.killer.ksport.im.client.client.ImClient;
import com.killer.ksport.im.client.service.IWebSocketService;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.java_websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author ：Killer
 * @date ：Created in 19-7-26 上午11:07
 * @description：${description}
 * @modified By：
 * @version: version
 */
@Service
public class WebSocketService implements IWebSocketService {


    @Override
    public void groupSending(String message) {
        Channel channel= ImClient.getChannel();
        channel.writeAndFlush(new TextWebSocketFrame(message));
    }


}
