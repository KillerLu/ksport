package com.killer.lesson15;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * @author ：Killer
 * @date ：Created in 19-7-31 上午10:28
 * @description：${description}
 * @modified By：
 * @version: version
 */

public class HelloWorldClientHandler extends ChannelInboundHandlerAdapter {

    public static final AttributeKey<String> NETTY_CHANNEL_KEY = AttributeKey.valueOf("netty.token");

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Attribute<String> attr = ctx.attr(NETTY_CHANNEL_KEY);
        String token = attr.get();
        System.out.println("channelActive"+"-----"+token);
        if (token == null) {
            token = attr.setIfAbsent("eyJhbGciOiJIUzUxMiJ9.eyJjcmVhdGVkIjoxNTYzMzUzMzY3MTI0LCJleHAiOjE1NjM5NTgxNjcsInVzZXJJZCI6MX0.6onsufNNr9ivBOi6-b1VqszL6RztPhCpbqWLmjxc5JyK3iaBEtzDeRc3x8TdQnmf5B8wBxQs373JBOsj");
        }
        ctx.channel().writeAndFlush("aaa");
        System.out.println("HelloWorldClientHandler Active");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Attribute<String> attr = ctx.attr(NETTY_CHANNEL_KEY);
        String token = attr.get();
        System.out.println("read"+"-----"+token);
        if (token == null) {
            token = attr.setIfAbsent("eyJhbGciOiJIUzUxMiJ9.eyJjcmVhdGVkIjoxNTYzMzUzMzY3MTI0LCJleHAiOjE1NjM5NTgxNjcsInVzZXJJZCI6MX0.6onsufNNr9ivBOi6-b1VqszL6RztPhCpbqWLmjxc5JyK3iaBEtzDeRc3x8TdQnmf5B8wBxQs373JBOsj");
        }
        System.out.println("HelloWorldClientHandler read Message:"+msg);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}

