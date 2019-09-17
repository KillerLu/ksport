package com.killer.ksport.quartz.demo;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author ：Killer
 * @date ：Created in 19-7-15 上午9:42
 * @description：${description}
 * @modified By：
 * @version: version
 */
public class HelloworldJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Hello world!:" + jobExecutionContext.getJobDetail().getKey());
    }
}