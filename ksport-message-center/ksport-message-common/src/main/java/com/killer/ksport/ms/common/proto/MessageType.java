package com.killer.ksport.ms.common.proto;

/**
 * @author ：Killer
 * @date ：Created in 19-8-1 下午2:28
 * @description：${description}
 * @modified By：
 * @version: version
 */
public interface MessageType {

    /**
     * 登录指令
     */
    public final static int AUTH=1;

    /**
     * Ping消息
     */
    public final static int PING=2;

    /**
     * PONG消息
     */
    public final static int PONG=3;

    /**
     * 文本消息
     */
    public final static int TEXT=4;
}
