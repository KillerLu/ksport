package com.killer.ksport.message.listener;

import com.alibaba.fastjson.JSONObject;
import com.killer.ksport.common.core.config.RabbitMQConfig;
import com.killer.ksport.common.core.controller.BaseController;
import com.killer.ksport.common.core.db.view.message.TransactionMessage;
import com.killer.ksport.common.core.enums.RequestTypeEnum;
import com.killer.ksport.common.core.model.TransactionMsgBody;
import com.killer.ksport.message.service.ITransactionMsgService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：Killer
 * @date ：Created in 19-9-12 下午2:58
 * @description：${description}
 * @modified By：
 * @version: version
 */
@Component
public class TransactionMsgQueueListener {

    protected final static Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ITransactionMsgService transactionMsgService;

    @RabbitHandler
    @RabbitListener(queues = RabbitMQConfig.TRANSACTION_MSG_QUEUE)
    public void receive(String message) {
        TransactionMessage transactionMessage=null;
        TransactionMsgBody transactionMsgBody=null;
        try {
            transactionMessage = JSONObject.parseObject(message, TransactionMessage.class);
            if (transactionMessage != null) {
                transactionMsgBody = JSONObject.parseObject(transactionMessage.getMsg(), TransactionMsgBody.class);
            }
        } catch (Exception e) {
            logger.error("解析事务消息异常",message);
        }

        if (transactionMsgBody == null || transactionMessage == null || StringUtils.isBlank(transactionMessage.getReceiverName()) || StringUtils.isBlank(transactionMsgBody.getUrl())) {
            logger.error("事务消息不正确,无法进行相应的业务操作",message);
            return;
        }
        String url = String.format("http://%s", transactionMessage.getReceiverName());
        url=url+transactionMsgBody.getUrl();
        Object result=null;
        if (RequestTypeEnum.GET.getValue().equals(transactionMsgBody.getRequestType())) {
            //TODO
            //暂时没有以GET提交的修改数据的
        }else{
            MultiValueMap<String, Object> map= new LinkedMultiValueMap<String, Object>();
            if (transactionMsgBody.getParams() != null && !transactionMsgBody.getParams().isEmpty()) {
                for (Map.Entry<String, Object> entry : transactionMsgBody.getParams().entrySet()) {
                    map.set(entry.getKey(), entry.getValue());
                }
            }
            result = this.restTemplate.postForObject(url, map, Object.class);
        }
        if (result != null) {
            Map<String,Object> retMap=(HashMap<String,Object>)result;
            int code=Integer.parseInt(retMap.get("code").toString());
            if (code==200) {
                //若操作成功,则应答该消息已经成功被消费
                transactionMsgService.acknowledgement(transactionMessage.getUuid());
            }
        }
    }

}
