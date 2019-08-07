package com.killer.ksport.im.server.session;

import com.alibaba.fastjson.JSON;
import com.killer.ksport.ms.common.model.Message;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.ImmediateEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author ：Killer
 * @date ：Created in 19-8-1 下午3:21
 * @description：${description}
 * @modified By：
 * @version: version
 */
public class SessionManager {

    //key是用户id,ChannelGroup为对应的用户连接集体(由于一个用户可能在多台设备上登录,因此需要用集体维护)
    public static ConcurrentMap<Long, ChannelGroup> userChannelGroup = new ConcurrentHashMap<Long, ChannelGroup>();


    /**
     * 给该用户id添加该channel
     * @param userId
     * @param channel
     */
    public static void add(Long userId, Channel channel) {
        if (userChannelGroup.containsKey(userId)) {
            //如果该用户id已经登录过,直接往里面添加即可
            userChannelGroup.get(userId).add(channel);
        } else {
            ChannelGroup group = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
            group.add(channel);
            userChannelGroup.put(userId, group);
        }
    }

    public static ChannelGroup get(Long userId) {
        return userChannelGroup.get(userId);
    }

    /**
     * 根据当前channel,查询当前的userId
     * @param channel
     * @return
     */
    public static Long getUserIdByChannel(Channel channel) {
        for (Map.Entry<Long, ChannelGroup> entry : userChannelGroup.entrySet()) {
            if (entry.getValue().contains(channel)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * 当Channel关闭时, 会自动从ChannelGroup中移除.
     * 如果某个userId对应的ChannelGroup元素个数 < 1, 则从userChannelGroup中移除该userId
     */
    public static void remove(Channel channel)
    {
        //获取当前channel所属用户
        Long userId=getUserIdByChannel(channel);
        if (userId == null) {
            return;
        }
        userChannelGroup.get(userId).remove(channel);
        if (userChannelGroup.get(userId) != null && userChannelGroup.get(userId).size() < 1) {
            userChannelGroup.remove(userId);
        }
    }

    public static void appointChat(Message message){
        ChannelGroup channelGroup = get(message.getToUserId());
        channelGroup.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(message)));
    }


    public static void groupChat(Message message) {
        if (userChannelGroup == null) {
            return;
        }
        for (Map.Entry<Long, ChannelGroup> entry : userChannelGroup.entrySet()) {
            if (entry.getKey().longValue() == message.getUserId().longValue()) {
                continue;
            }
            entry.getValue().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(message)));
        }
    }

}
