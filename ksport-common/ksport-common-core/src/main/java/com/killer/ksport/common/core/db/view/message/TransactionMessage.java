package com.killer.ksport.common.core.db.view.message;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * 事务消息表
 *
 * @author killer
 * @date 2019-09-16
 */
@TableName("t_transaction_message")
public class TransactionMessage extends Model<TransactionMessage> {

    private static final long serialVersionUID = 1L;


    //重写这个方法，return当前类的主键
    @Override
    protected Serializable pkVal() {
        return id;
    }


    /**
     * 主键id
     */
    @TableId
    private Long id;
    /**
     * 事务的唯一标识id
     */
    private String uuid;
    /**
     * 消息主题,json格式
     */
    private String msg;
    /**
     * 消息状态 1:待确认,2:待发送,3:已接收
     */
    private Integer msgStatus;
    /**
     * 该事务要执行的操作
     */
    private String action;
    /**
     * 生产者服务名称
     */
    private String senderName;
    /**
     * 接受者服务名称
     */
    private String receiverName;
    /**
     * 消息发送到mq的时间
     */
    private Date sendTime;
    /**
     * 重试次数(用于定时任务扫描因异常没有被正确处理的消息)
     */
    private Integer retryTime;
    /**
     * 是否删除标志 0:未删除,1:已删除
     */
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Boolean deleted;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;


    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return this.id;
    }


    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getUuid() {
        return this.uuid;
    }


    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return this.msg;
    }


    public void setMsgStatus(Integer msgStatus) {
        this.msgStatus = msgStatus;
    }
    public Integer getMsgStatus() {
        return this.msgStatus;
    }


    public void setAction(String action) {
        this.action = action;
    }
    public String getAction() {
        return this.action;
    }


    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
    public String getSenderName() {
        return this.senderName;
    }


    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
    public String getReceiverName() {
        return this.receiverName;
    }


    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
    public Date getSendTime() {
        return this.sendTime;
    }


    public void setRetryTime(Integer retryTime) {
        this.retryTime = retryTime;
    }
    public Integer getRetryTime() {
        return this.retryTime;
    }


    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
    public Boolean isDeleted() {
        return this.deleted;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getCreateTime() {
        return this.createTime;
    }


    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
    public Date getModifyTime() {
        return this.modifyTime;
    }

}
