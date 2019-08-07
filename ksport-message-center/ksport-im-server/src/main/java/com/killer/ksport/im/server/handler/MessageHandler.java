package com.killer.ksport.im.server.handler;

import com.alibaba.fastjson.JSON;
import com.killer.ksport.im.server.session.SessionManager;
import com.killer.ksport.ms.common.model.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description 广播返回用户的信息
 */
public class MessageHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

    /**
     * 经过UserAuthHandler的处理后,能到达MessageHandler的只有TextWebSocketFrame类型的消息
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame)
            throws Exception {
        Message message = JSON.parseObject(frame.text(), Message.class);
        if (message.getToUserId() != null) {
            //私发
            SessionManager.appointChat(message);
        }else{
            //群发
            SessionManager.groupChat(message);
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        SessionManager.remove(ctx.channel());
        super.channelUnregistered(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("connection error and close the channel", cause);
        SessionManager.remove(ctx.channel());
    }

}
