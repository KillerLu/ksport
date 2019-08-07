package com.killer.lesson1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

/**
 *
 *
 *
 *
 *                   ________________________                                 __________________________
 *                  |                        |                               |                          |
 *                  |   <-----Inbound-----   |                               |   ---inbound------- >    |   ________
 *                  |   _____        ______  |                               |    _______      ____     |  |        |
 *      _______     |  |     |       |    |  |                               |    |     |     |    |    |  |        |
 *     |       |    |  |  ②  |       |  ③ |  |      ___________________      |    |  ⑤  |     |  ⑥ |    |  |        |
 *     |       |    |  |_____|       |____|  |     |                   |     |    |_____|     |____|    |  |        |
 *     |client |----|-------______-----------|-----|      network      |-----|--------------------------|--| server |
 *     |       |    |       |     |          |     |___________________|     |          ______          |  |        |
 *     |       |    |       |  ①  |          |                               |          |     |         |  |        |
 *     |       |    |       |_____|          |                               |          |  ④  |         |  |________|
 *     |       |    |                        |                               |          |_____|         |
 *     |_______|    |   -----Outbound--->    |                               |    <-----outbound----    |
 *                  |___ChannelPipeline______|                               |______ChannelPipeline_____|
 *
 *  ①：StringEncoder继承于MessageToMessageEncoder，而MessageToMessageEncoder又继承于ChannelOutboundHandlerAdapter
 *  ②：HelloWorldClientHandler.java
 *  ③：StringDecoder继承于MessageToMessageDecoder，而MessageToMessageDecoder又继承于ChannelInboundHandlerAdapter
 *  ④：StringEncoder 编码器
 *  ⑤：StringDecoder 解码器
 *  ⑥：HelloWorldServerHandler.java
 *
 *
 *
 */
public class HelloWorldServer {

    private int port;

    public HelloWorldServer(int port) {
        this.port = port;
    }

    public void start(){
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //Netty的服务器端,需要一个ServerBootstrap
            //我们需要指定它的transports，是NIO还是OIO,还需要指定端口号，
            // 最最重要的是安装server端的处理器，也就是我们之前写的HelloWorldServerHandler，还有一些Option的配置
            //server端还有一点需要注意，就是需要关闭连接，释放线程资源
            ServerBootstrap sbs = new ServerBootstrap().group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        protected void initChannel(SocketChannel ch) throws Exception {
//                            ch.pipeline().addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                            ch.pipeline().addLast("decoder", new StringDecoder());
                            ch.pipeline().addLast("encoder", new StringEncoder());
                            ch.pipeline().addLast(new HelloWorldServerHandler());
                        };

                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            // 绑定端口，开始接收进来的连接
            ChannelFuture future = sbs.bind(port).sync();

            System.out.println("Server start listen at " + port );
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new HelloWorldServer(port).start();
    }
}

