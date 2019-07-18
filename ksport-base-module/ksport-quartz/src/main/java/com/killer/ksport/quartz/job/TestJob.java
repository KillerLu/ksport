package com.killer.ksport.quartz.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author ：Killer
 * @date ：Created in 19-7-18 上午11:56
 * @description：${description}
 * @modified By：
 * @version: version
 */
@DisallowConcurrentExecution//不允许并发执行
public class TestJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("aaaaaa");
    }
}
