package com.killer.ksport.ms.common.model;

import java.io.Serializable;

/**
 * @author ：Killer
 * @date ：Created in 19-8-1 下午2:26
 * @description：${description}
 * @modified By：
 * @version: version
 */
public class Message implements Serializable {

    /**
     * 发送方用户id
     */
    private Long userId;

    /**
     * 接收方用户id
     */
    private Long toUserId;

    /**
     * 消息类型
     */
    private Integer msgType;

    /**
     * 消息内容
     */
    private String content;

    public Message(){}

    public Message(Long userId, Long toUserId, Integer msgType, String content) {
        this.userId = userId;
        this.toUserId = toUserId;
        this.msgType = msgType;
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "userId=" + userId +
                ", toUserId=" + toUserId +
                ", msgType=" + msgType +
                ", content='" + content + '\'' +
                '}';
    }
}
