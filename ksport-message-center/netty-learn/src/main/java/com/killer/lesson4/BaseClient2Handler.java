package com.killer.lesson4;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author ：Killer
 * @date ：Created in 19-7-31 上午11:05
 * @description：${description}
 * @modified By：
 * @version: version
 */
public class BaseClient2Handler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("BaseClient2Handler Active");
    }



}

