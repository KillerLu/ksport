package com.killer.ksport.im.client.client;

import com.alibaba.fastjson.JSON;
import com.killer.ksport.im.client.handler.WebSocketClientHandler;
import com.killer.ksport.ms.common.model.Message;
import com.killer.ksport.ms.common.proto.MessageType;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：Killer
 * @date ：Created in 19-8-5 上午11:33
 * @description：${description}
 * @modified By：
 * @version: version
 */
@Component
public class ImClient {

    protected final static Logger logger = LoggerFactory.getLogger(ImClient.class);

    public final static List<Channel> CHANNEL_POOL=new ArrayList<Channel>();

    public static Channel getChannel(){
        if (!CollectionUtils.isEmpty(CHANNEL_POOL)) {
            return CHANNEL_POOL.get(0);
        }
        return null;
    }

    @Value("${server.port}")
    private int port;

    @PostConstruct
    public void start() throws Exception {
        //这里必须要新开一个线程去启动netty,要不然会将运行tomcat的线程占用掉,导致无法接受http请求
        new Thread(){
            @Override
            public void run(){
                EventLoopGroup group=new NioEventLoopGroup();
                try {
                    Bootstrap boot=new Bootstrap();
                    boot.option(ChannelOption.SO_KEEPALIVE,true)
                            .option(ChannelOption.TCP_NODELAY,true)
                            .option(ChannelOption.SO_BACKLOG,1024*1024*10)
                            .group(group)
                            .handler(new LoggingHandler(LogLevel.INFO))
                            .channel(NioSocketChannel.class)
                            .handler(new ChannelInitializer<SocketChannel>() {
                                protected void initChannel(SocketChannel socketChannel) throws Exception {
                                    ChannelPipeline p = socketChannel.pipeline();
                                    p.addLast(new ChannelHandler[]{new HttpClientCodec(),
                                            new HttpObjectAggregator(1024*1024*10)});
                                    p.addLast("hookedHandler", new WebSocketClientHandler());
                                }
                            });
                    URI uri = new URI("ws://localhost:9050/websocket");
                    HttpHeaders httpHeaders = new DefaultHttpHeaders();
                    //进行握手
                    WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(uri, WebSocketVersion.V13, (String)null, true,httpHeaders);
                    System.out.println("connect");
                    final Channel channel=boot.connect(uri.getHost(),uri.getPort()).sync().channel();
                    CHANNEL_POOL.add(channel);
                    WebSocketClientHandler handler = (WebSocketClientHandler)channel.pipeline().get("hookedHandler");
                    handler.setHandshaker(handshaker);
                    handshaker.handshake(channel);
                    //阻塞等待是否握手成功
                    handler.handshakeFuture().sync();
                    Message msg = new Message(3l, null, MessageType.AUTH, null);
                    channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msg)));
                    channel.closeFuture().sync(); // 关闭服务器通道
                } catch (InterruptedException | URISyntaxException e) {
                    logger.error("socket连接异常:{}", e);
                    e.printStackTrace();
                } finally {
                    group.shutdownGracefully();
                }
            }
        }.start();

    }
}
