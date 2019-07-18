package com.killer.ksport.quartz.job;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 关键对象分为三个：

 1、调度工作类：org.springframework.scheduling.quartz.JobDetailBean，该对象通过jobClass属性指定调度工作类；

 2、调度触发器：org.springframework.scheduling.quartz.CronTriggerBean，该对象通过jobDetail属性指定工作类，通过

 cronExpression属性指定调度频率；

 3、调度工厂类：org.springframework.scheduling.quartz.SchedulerFactoryBean，该对象通过triggers属性指定单个或多个触发器
 ---------------------
 */
@DisallowConcurrentExecution//不允许并发执行
public class DynamicJob implements Job {

    private Logger logger = LoggerFactory.getLogger(DynamicJob.class);
    /**
     * 核心方法,Quartz Job真正的执行逻辑.
     * @param executorContext executorContext JobExecutionContext中封装有Quartz运行所需要的所有信息
     * @throws JobExecutionException execute()方法只允许抛出JobExecutionException异常
     */
    @Override
    public void execute(JobExecutionContext executorContext) throws JobExecutionException {
        //JobDetail中的JobDataMap是共用的,从getMergedJobDataMap获取的JobDataMap是全新的对象
        JobDataMap map = executorContext.getMergedJobDataMap();
        String jarPath = map.getString("jarPath");
        String parameter = map.getString("parameter");
        String vmParam = map.getString("vmParam");
        logger.info("Running Job name : {} ", map.getString("name"));
        logger.info("Running Job description : " + map.getString("JobDescription"));
        logger.info("Running Job group: {} ", map.getString("group"));
        logger.info("Running Job cron : " + map.getString("cronExpression"));
        logger.info("Running Job jar path : {} ", jarPath);
        logger.info("Running Job parameter : {} ", parameter);
        logger.info("Running Job vmParam : {} ", vmParam);
        long startTime = System.currentTimeMillis();
//        if (!StringUtils.getStringUtil.isEmpty(jarPath)) {
//            File jar = new File(jarPath);
//            if (jar.exists()) {
//                ProcessBuilder processBuilder = new ProcessBuilder();
//                processBuilder.directory(jar.getParentFile());
//                List<String> commands = new ArrayList<>();
//                commands.add("java");
//                if (!StringUtils.getStringUtil.isEmpty(vmParam)) commands.add(vmParam);
//                commands.add("-jar");
//                commands.add(jarPath);
//                if (!StringUtils.getStringUtil.isEmpty(parameter)) commands.add(parameter);
//                processBuilder.command(commands);
//                logger.info("Running Job details as follows >>>>>>>>>>>>>>>>>>>>: ");
//                logger.info("Running Job commands : {}  ", StringUtils.getStringUtil.getListString(commands));
//                try {
//                    Process process = processBuilder.start();
//                    logProcess(process.getInputStream(), process.getErrorStream());
//                } catch (IOException e) {
//                    throw new JobExecutionException(e);
//                }
//            } else throw new JobExecutionException("Job Jar not found >>  " + jarPath);
//        }
        long endTime = System.currentTimeMillis();
        logger.info(">>>>>>>>>>>>> Running Job has been completed , cost time :  " + (endTime - startTime) + "ms\n");
    }


}
