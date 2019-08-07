package com.killer.lesson15;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * @author ：Killer
 * @date ：Created in 19-7-31 上午10:23
 * @description：${description}
 * @modified By：
 * @version: version
 */
public class HelloWorldServerHandler extends ChannelInboundHandlerAdapter {


    public static final AttributeKey<String> NETTY_CHANNEL_KEY = AttributeKey.valueOf("netty.token");

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Attribute<String> attr = ctx.attr(NETTY_CHANNEL_KEY);
        String token = attr.get();
        System.out.println("read"+"-----"+token);
        System.out.println("server channelRead..");
        System.out.println(ctx.channel().remoteAddress()+"->Server :"+ msg.toString());
        ctx.write("server write"+msg);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}

