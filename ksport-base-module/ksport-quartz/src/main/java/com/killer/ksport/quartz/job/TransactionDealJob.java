package com.killer.ksport.quartz.job;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.killer.ksport.common.core.constant.TransactionActionConstant;
import com.killer.ksport.common.core.db.view.message.TransactionMessage;
import com.killer.ksport.common.core.model.TransactionMsgBody;
import com.killer.ksport.common.core.service.impl.BaseService;
import com.killer.ksport.message.api.TransactionMsgApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author ：Killer
 * @date ：Created in 19-9-20 下午2:24
 * @description：${description}
 * @modified By：
 * @version: version
 */
public abstract class TransactionDealJob {

    @Autowired
    private TransactionMsgApi transactionMsgApi;

    protected final static Logger logger = LoggerFactory.getLogger(BaseService.class);
    /**
     * 统一处理需要被处理的事务消息(如过期未被确认或过期未被应答的)
     */
    protected void dealTransactionMessage(){
        List<TransactionMessage> transactionMessages=listDueTimeMessage();
        if (CollectionUtils.isNotEmpty(transactionMessages)) {
            for (TransactionMessage transactionMessage : transactionMessages) {
                long keyId = getKeyIdFromTransactionMsg(transactionMessage);
                if (keyId == 0) {
                    //若解析不出keyId,证明该事务消息有误,直接删除被记录错误日志(前面已记录)
                    transactionMsgApi.deleteTransactionMsgById(transactionMessage.getId());
                    return;
                }
                Object result = checkTransactionStatus(keyId,transactionMessage.getAction());
                try {
                    //result为空时会报空指针，catch会进行处理
                    Map<String, Object> retMap = (Map<String, Object>) result;
                    if ((Boolean) retMap.get("data") == false) {
                        //如果该用户已经删除,则确认该消息,并将消息发送到消费者让其完成事务
                        transactionMsgApi.confirmMsgToSend(transactionMessage.getUuid());
                    } else {
                        //如果用户没有被删除,证明本地事务没有完成,则直接删除该条事务消息
                        transactionMsgApi.deleteTransactionMsgById(transactionMessage.getId());
                    }
                } catch (Exception e) {
                    //如果没有消息返回或者解析失败，则证明该次尝试失败,则尝试次数加1
                    transactionMessage.setRetryTime(transactionMessage.getRetryTime()+1);
                    transactionMsgApi.updateTransactionMsg(transactionMessage);
                    if (transactionMessage.getRetryTime() >= 5) {
                        //消息最多重试5次，如果仍然不成功，则记录日志(因为系统不再尝试恢复该数据，需要人工恢复)
                        logger.error("id:"+transactionMessage.getId()+"尝试失败次数已达到5次，该条数据需要人工介入恢复");
                    }
                }
            }
        }
    }




    private long getKeyIdFromTransactionMsg(TransactionMessage transactionMessage) {
        long keyId = 0;
        try {
            TransactionMsgBody transactionMsgBody = JSON.parseObject(transactionMessage.getMsg(), TransactionMsgBody.class);
            keyId = transactionMsgBody.getKeyId();
        } catch (Exception e) {
            logger.error("解析事务消息体失败");
        }
        return keyId;
    }

    /**
     * 获取需要被处理的事务消息(超时未确认或未应答的)
     * @return
     */
    protected abstract List<TransactionMessage> listDueTimeMessage();

    /**
     * 查询该事务消息的对应事务状态已确认下一步操作是删除还是确认
     * @param keyId
     * @return
     */
    protected abstract Object checkTransactionStatus(long keyId,String action);
}
