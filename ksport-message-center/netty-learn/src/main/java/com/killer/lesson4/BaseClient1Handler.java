package com.killer.lesson4;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author ：Killer
 * @date ：Created in 19-7-31 上午11:04
 * @description：${description}
 * @modified By：
 * @version: version
 */

public class BaseClient1Handler extends ChannelInboundHandlerAdapter {



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("BaseClient1Handler channelActive");
        //触发下一个同类型channelHandle对应的方法(Inbound触发Inbound,Outbount触发Outbount)
        //也就是说如果一个channelPipeline中有多个channelHandler时，
        // 且这些channelHandler中有同样的方法时，例如这里的channelActive方法，
        // 只会调用处在第一个的channelHandler中的channelActive方法，
        // 如果你想要调用后续的channelHandler的同名的方法就需要调用以“fire”为开头的方法了
        ctx.fireChannelActive();
    }

    /**
     * 目前来说这样做的好处：

     1）每一个handler只需要关注自己要处理的方法，如果你不关注channelActive方法时，
        你自定义的channelhandler就不需要重写channelActive方法

     2）异常处理，如果 exceptionCaught方法每个handler都重写了，
        只需有一个类捕捉到然后做处理就可以了，不需要每个handler都处理一遍
     */

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("BaseClient1Handler channelInactive");
    }

}

