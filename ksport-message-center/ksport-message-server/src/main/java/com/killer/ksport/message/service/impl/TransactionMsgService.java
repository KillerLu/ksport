package com.killer.ksport.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.killer.ksport.common.core.config.RabbitMQConfig;
import com.killer.ksport.common.core.db.dao.message.TransactionMessageDao;
import com.killer.ksport.common.core.db.view.message.TransactionMessage;
import com.killer.ksport.common.core.exception.CommonException;
import com.killer.ksport.common.core.service.impl.BaseService;
import com.killer.ksport.message.enums.MsgStatusEnum;
import com.killer.ksport.message.service.ITransactionMsgService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author ：Killer
 * @date ：Created in 19-9-11 下午3:49
 * @description：${description}
 * @modified By：
 * @version: version
 */
@Service
public class TransactionMsgService extends BaseService<TransactionMessageDao,TransactionMessage> implements ITransactionMsgService{

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void prepareTransactionMsg(String uuid, String msg,String action,String senderName,String receiverName) {
        super.save(buildPrepareMessage(uuid, msg,action,senderName,receiverName));
    }

    @Override
    public void confirmMsgToSend(String uuid) {
        TransactionMessage transactionMessage=updateTransactionMsgStatusByUuId(uuid,MsgStatusEnum.CONFIRM.getValue());
        //同时往队列投递消息,让消费者消费
        rabbitTemplate.convertAndSend(RabbitMQConfig.TRANSACTION_MSG_QUEUE, RabbitMQConfig.TRANSACTION_MSG_QUEUE, JSON.toJSONString(transactionMessage));
    }

    @Override
    public void acknowledgement(String uuid) {
        updateTransactionMsgStatusByUuId(uuid,MsgStatusEnum.ACK.getValue());
    }

    private TransactionMessage buildPrepareMessage(String uuid, String msg,String action,String senderName,String receiverName){
        TransactionMessage transactionMessage=new TransactionMessage();
        transactionMessage.setSenderName(senderName);
        transactionMessage.setReceiverName(receiverName);
        transactionMessage.setUuid(uuid);
        transactionMessage.setMsg(msg);
        transactionMessage.setAction(action);
        //失败尝试次数刚开始为0
        transactionMessage.setRetryTime(0);
        //这是预发送消息,因此在数据库保存状态为待确认
        transactionMessage.setMsgStatus(MsgStatusEnum.PREPARE.getValue());
        return transactionMessage;
    }

    /**
     * 根据事务消息uuid根据消息状态
     * @param uuid
     * @param msgStatus
     * @return
     */
    private TransactionMessage updateTransactionMsgStatusByUuId(String uuid,int msgStatus) {
        //根据uuid查出该条待确认消息
        TransactionMessage transactionMessage=baseMapper.selectOne(new QueryWrapper<TransactionMessage>().eq("uuid", uuid));
        if (transactionMessage == null) {
            throw new CommonException("该事务消息不存在");
        }
        if (MsgStatusEnum.CONFIRM.getValue() == msgStatus) {
            //如果是确认状态,则代表消息即将要发送,这里设置发送时间
            transactionMessage.setSendTime(new Date());
        }
        //更改消息状态
        transactionMessage.setMsgStatus(msgStatus);
        super.updateById(transactionMessage);
        return transactionMessage;
    }
}
