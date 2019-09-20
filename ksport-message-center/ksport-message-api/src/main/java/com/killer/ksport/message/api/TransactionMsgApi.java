package com.killer.ksport.message.api;

import com.killer.ksport.common.core.db.view.message.TransactionMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author ：Killer
 * @date ：Created in 19-9-11 下午5:06
 * @description：${description}
 * @modified By：
 * @version: version
 */
@Service
@FeignClient(value = "ksport-message")
public interface TransactionMsgApi {

    /**
     * 预发送消息，先将消息保存到消息中心(状态为待确认,类似TCC操作)
     */
    @RequestMapping(value = "prepare", method = RequestMethod.POST)
    Object prepareTransactionMsg(
            @RequestParam("uuid") String uuid,
            @RequestParam("msg") String msg,
            @RequestParam("action") String action,
            @RequestParam("senderName")String senderName,
            @RequestParam("receiverName")String receiverName);

    /**
     * 生产者确认消息可投递
     */
    @RequestMapping(value = "confirm", method = RequestMethod.POST)
    Object confirmMsgToSend(@RequestParam("uuid") String uuid);


    /**
     * 消费者确认消费成功
     */
    @RequestMapping(value = "ack", method = RequestMethod.POST)
    Object acknowledgement(@RequestParam("uuid") String uuid);

    /**
     * 获取逾期仍然还是待确认的消息
     * @return
     */
    @RequestMapping(value = "listUnConfirmMessage", method = RequestMethod.GET)
    List<TransactionMessage> listUnConfirmMessage();

    /**
     * 根据id删除事务消息
     * @param id
     */
    @RequestMapping(value = "deleteTransactionMsgById", method = RequestMethod.GET)
    void deleteTransactionMsgById(@RequestParam("id")Long id);

    /**
     * 更新事务消息
     * @param transactionMessage
     */
    @RequestMapping(value = "updateTransactionMsg", method = RequestMethod.POST)
    Object updateTransactionMsg(@RequestBody TransactionMessage transactionMessage);

    /**
     * 获取预期仍然还是待发送的消息
     * @return
     */
    @RequestMapping(value = "listUnAckMessage", method = RequestMethod.GET)
    List<TransactionMessage> listUnAckMessage();
}
