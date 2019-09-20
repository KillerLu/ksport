package com.killer.ksport.message.service;

import com.killer.ksport.common.core.db.view.message.TransactionMessage;
import com.killer.ksport.common.core.service.IBaseService;

import java.util.List;

/**
 * @author ：Killer
 * @date ：Created in 19-9-11 下午3:47
 * @description：${description}
 * @modified By：
 * @version: version
 */
public interface ITransactionMsgService extends IBaseService<TransactionMessage> {

    /**
     * 保存预发送消息
     */
    public void prepareTransactionMsg(String uuid, String msg,String action,String sendName,String receiverName);

    /**
     * 确认某预发送消息,并更改为待发送状态,同时将该消息推送到队列
     * @param uuid
     */
    public void confirmMsgToSend(String uuid);

    /**
     * 消费者确认已消费该消息
     * @param uuid
     */
    public void acknowledgement(String uuid);

    /**
     * 查询逾期未确认的事务消息
     * @return
     */
    public List<TransactionMessage> listUnConfirmMessage();

    /**
     * 查询逾期未应答的事务消息
     * @return
     */
    List<TransactionMessage> listUnAckMessage();
}
