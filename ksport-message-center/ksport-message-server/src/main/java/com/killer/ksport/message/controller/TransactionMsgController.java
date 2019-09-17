package com.killer.ksport.message.controller;

import com.killer.ksport.common.core.controller.BaseController;
import com.killer.ksport.common.core.controller.ResponseBuilder;
import com.killer.ksport.common.core.db.view.message.TransactionMessage;
import com.killer.ksport.message.api.TransactionMsgApi;
import com.killer.ksport.message.enums.MsgStatusEnum;
import com.killer.ksport.message.service.ITransactionMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：Killer
 * @date ：Created in 19-9-11 下午3:41
 * @description：${description}
 * @modified By：
 * @version: version
 */
@RestController
public class TransactionMsgController extends BaseController implements TransactionMsgApi{

    @Autowired
    private ITransactionMsgService transactionMsgService;

    @Override
    public Object prepareTransactionMsg(String uuid,String msg,String action,String senderName,String receiverName){
        ResponseBuilder builder = new ResponseBuilder();
        transactionMsgService.prepareTransactionMsg(uuid, msg,action,senderName,receiverName);
        return builder.success().build();
    }

    @Override
    public Object confirmMsgToSend(String uuid) {
        ResponseBuilder builder = new ResponseBuilder();
        transactionMsgService.confirmMsgToSend(uuid);
        return builder.success().build();
    }

    @Override
    public Object acknowledgement(String uuid) {
        ResponseBuilder builder = new ResponseBuilder();
        transactionMsgService.acknowledgement(uuid);
        return builder.success().build();
    }

    @Override
    public Object listUnConfirmMessage() {
        return null;
    }

}
