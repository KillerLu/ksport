//package com.killer.ksport.quartz.demo;
//
//import org.quartz.*;
//import org.quartz.impl.StdSchedulerFactory;
//
///**
// * Quartz保存工作数据默认是使用内存的方式，
// * 该简单例子启动时可以在控制台日志中看到JobStore是RAMJobStore使用内存的模式，
// * 然后是not clustered表示不是集群中的节点
// *
// *  Scheduler class: 'org.quartz.core.QuartzScheduler' - running locally.
// *  NOT STARTED.
// *  Currently in standby mode.
// *  Number of jobs executed: 0
// *  Using thread pool 'org.quartz.simpl.SimpleThreadPool' - with 10 threads.
// *  Using job-store 'org.quartz.simpl.RAMJobStore' - which does not support persistence. and is not clustered.
// */
//public class SimpleQuartzDemo {
//    public static void main(String[] args) throws Exception {
//        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
//        Scheduler scheduler = schedulerFactory.getScheduler();
//        // 启动scheduler
//        scheduler.start();
//        // 创建HelloworldJob的JobDetail实例，并设置name/group
//        JobDetail jobDetail = JobBuilder.newJob(HelloworldJob.class)
//                .withIdentity("myJob","myJobGroup1")
//                //JobDataMap可以给任务传递参数
//                .usingJobData("job_param","job_param1")
//                .build();
//        // 创建Trigger触发器设置使用cronSchedule方式调度
//        Trigger trigger = TriggerBuilder.newTrigger()
//                .withIdentity("myTrigger","myTriggerGroup1")
//                .usingJobData("job_trigger_param","job_trigger_param1")
//                .startNow()
//                //.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
//                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
//                .build();
//        // 注册JobDetail实例到scheduler以及使用对应的Trigger触发时机
//        scheduler.scheduleJob(jobDetail,trigger);
//    }
//}