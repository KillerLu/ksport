package com.killer.ksport.im.server.server;

import com.killer.ksport.im.server.handler.MessageHandler;
import com.killer.ksport.im.server.handler.UserAuthHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author ：Killer
 * @date ：Created in 19-7-31 下午5:00
 * @description：${description}
 * @modified By：
 * @version: version
 */
@Component
public class ImServer {


    protected final static Logger logger = LoggerFactory.getLogger(ImServer.class);

    @Value("${server.port}")
    private int port;

    @PostConstruct
    public void start() {
        new Thread(){
            @Override
            public void run(){
                //创建两组线程，监听连接和工作
                EventLoopGroup bossGroup = new NioEventLoopGroup();
                EventLoopGroup group = new NioEventLoopGroup();
                try {
                    //Netty用于启动Nio服务端的启动类
                    ServerBootstrap sb = new ServerBootstrap();
                    sb.option(ChannelOption.SO_BACKLOG, 1024);
                    // 绑定线程池
                    sb.group(group, bossGroup)
                            //注册NioServerSocketChannel,指定使用的channel
                            .channel(NioServerSocketChannel.class)
                            // 绑定监听端口
                            .localAddress(port)
                            //注册处理器,绑定客户端连接时候触发操作
                            .childHandler(new ChannelInitializer<SocketChannel>() {

                                @Override
                                protected void initChannel(SocketChannel ch) throws Exception {
                                    //设置log监听器，并且日志级别为debug，方便观察运行流程
                                    ch.pipeline().addLast("logging",new LoggingHandler("DEBUG"));//设置log监听器，并且日志级别为debug，方便观察运行流程
                                    //设置解码器,websocket协议本身是基于http协议的，所以这边也要使用http解编码器
                                    ch.pipeline().addLast("http-codec",new HttpServerCodec());
                                    //聚合器，使用websocket会用到
                                    ch.pipeline().addLast("aggregator",new HttpObjectAggregator(65536));
                                    //支持异步发送大的码流，一般用于发送文件流
                                    ch.pipeline().addLast("http-chunked",new ChunkedWriteHandler());//用于大数据的分区传输
                                    //检测链路是否读空闲
                                    //每隔5秒来检查一下channelRead方法被调用的情况，如果在5秒内该链上的channelRead方法都没有被触发，就会调用userEventTriggered方法
                                    ch.pipeline().addLast("idleState", new IdleStateHandler(5, 0, 0));
                                    //处理握手和认证
                                    ch.pipeline().addLast("userAuth",new UserAuthHandler());
                                    //处理消息的发送
                                    ch.pipeline().addLast("messageHandler",new MessageHandler());
                                }
                            });
                    ChannelFuture cf = sb.bind().sync(); // 服务器异步创建绑定
                    logger.info(ImServer.class + " 启动正在监听： " + cf.channel().localAddress());
                    cf.channel().closeFuture().sync(); // 关闭服务器通道
                } catch (InterruptedException e) {
                    logger.error("", e);
                } finally {
                    logger.info("关闭连接");
                    try {
                        group.shutdownGracefully().sync(); // 释放线程池资源
                        bossGroup.shutdownGracefully().sync();
                    } catch (InterruptedException e) {
                        logger.error("", e);
                    }

                }
            }
        }.start();
    }

}
