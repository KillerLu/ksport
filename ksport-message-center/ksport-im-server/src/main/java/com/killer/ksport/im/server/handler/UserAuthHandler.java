package com.killer.ksport.im.server.handler;

import com.alibaba.fastjson.JSON;
import com.killer.ksport.im.server.session.SessionManager;
import com.killer.ksport.ms.common.model.Message;
import com.killer.ksport.ms.common.proto.MessageType;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description 处理请求认证和分发消息
 */
public class UserAuthHandler extends SimpleChannelInboundHandler<Object> {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthHandler.class);

    private int loss_connect_time = 0;

    private WebSocketServerHandshaker handshaker;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            //websocket连接,第一次是借助与http握手的
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            handleWebSocket(ctx, (WebSocketFrame) msg);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent evnet = (IdleStateEvent) evt;
            // 判断Channel是否读空闲, 读空闲时移除Channel
            if (evnet.state().equals(IdleState.READER_IDLE)) {
                loss_connect_time++;
                logger.info("5 秒没有接收到客户端的信息了:"+String.valueOf(loss_connect_time));
                if (loss_connect_time > 2) {
                    logger.info("关闭这个不活跃的channel");
                    SessionManager.remove(ctx.channel());
                    ctx.channel().close();
                }
            }
        }
        ctx.fireUserEventTriggered(evt);
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
        if (!request.decoderResult().isSuccess() || !"websocket".equals(request.headers().get("Upgrade"))) {
            logger.warn("protobuf don't support websocket");
            ctx.channel().close();
            return;
        }
        WebSocketServerHandshakerFactory handshakerFactory = new WebSocketServerHandshakerFactory(
                "ws://localhost:9050/websocket", null, true);
        handshaker = handshakerFactory.newHandshaker(request);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            // 动态加入websocket的编解码处理
            handshaker.handshake(ctx.channel(), request);
        }
    }

    private void handleWebSocket(ChannelHandlerContext ctx, WebSocketFrame frame) {
        // 判断是否关闭链路命令
        if (frame instanceof CloseWebSocketFrame) {
            SessionManager.remove(ctx.channel());
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        // 判断是否Ping消息
        if (frame instanceof PingWebSocketFrame) {
            logger.info("ping message:{}", frame.content().retain());
            ctx.writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // 判断是否Pong消息
        if (frame instanceof PongWebSocketFrame) {
            logger.info("pong message:{}", frame.content().retain());
            ctx.writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // 如果是文本消息
        if (frame instanceof TextWebSocketFrame) {
            handleTextWebSocketFrame(ctx, (TextWebSocketFrame) frame);
        }

    }


    private void handleTextWebSocketFrame(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
        Message message = JSON.parseObject(frame.text(), Message.class);
        Channel channel = ctx.channel();
        switch (message.getMsgType()) {
            //上面已进行了PING,PONG消息的处理,这里只需要处理业务消息即可
            case MessageType.AUTH:
                //将该用户的会话保存起来
                SessionManager.add(message.getUserId(), channel);
                return;
            case MessageType.TEXT: //普通的消息留给MessageHandler处理
                break;
        }
        //后续消息交给MessageHandler处理
        ctx.fireChannelRead(frame.retain());
    }

}
