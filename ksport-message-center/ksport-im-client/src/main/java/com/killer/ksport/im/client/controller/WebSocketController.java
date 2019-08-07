package com.killer.ksport.im.client.controller;

import com.alibaba.fastjson.JSON;
import com.killer.ksport.im.client.service.IWebSocketService;
import com.killer.ksport.ms.common.model.Message;
import com.killer.ksport.ms.common.proto.MessageType;
import org.java_websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：Killer
 * @date ：Created in 19-7-26 上午11:16
 * @description：${description}
 * @modified By：
 * @version: version
 */
@RestController
@RequestMapping("/websocket")
public class WebSocketController {


    @Autowired
    private IWebSocketService webSocketService;

    @RequestMapping("/sendMessage")
    public String sendMessage(String message){
        Message msg = new Message(1l, 2l, MessageType.TEXT, message);
        webSocketService.groupSending(JSON.toJSONString(msg));
        return message;
    }

}
