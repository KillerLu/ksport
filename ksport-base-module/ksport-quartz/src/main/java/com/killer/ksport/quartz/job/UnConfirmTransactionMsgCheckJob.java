package com.killer.ksport.quartz.job;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.killer.ksport.common.core.constant.TransactionActionConstant;
import com.killer.ksport.common.core.db.view.message.TransactionMessage;
import com.killer.ksport.common.core.model.TransactionMsgBody;
import com.killer.ksport.common.core.service.impl.BaseService;
import com.killer.ksport.feign.api.service.UserServiceCaller;
import com.killer.ksport.message.api.TransactionMsgApi;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author ：Killer
 * @date ：Created in 19-9-17 上午10:44
 * @description：未确认的事务消息检测
 * @modified By：
 * @version: version
 */
@DisallowConcurrentExecution//不允许并发执行
public class UnConfirmTransactionMsgCheckJob extends TransactionDealJob implements Job {

    @Autowired
    private TransactionMsgApi transactionMsgApi;
    @Autowired
    private UserServiceCaller userServiceCaller;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        super.dealTransactionMessage();
    }


    @Override
    protected List<TransactionMessage> listDueTimeMessage() {
        return transactionMsgApi.listUnConfirmMessage();
    }

    @Override
    protected Object checkTransactionStatus(long keyId, String action) {
        switch (action) {
            case TransactionActionConstant.DELETE_GROUP_USER_BY_USERID:
                return userServiceCaller.checkUser(keyId);
        }
        return null;
    }


}
