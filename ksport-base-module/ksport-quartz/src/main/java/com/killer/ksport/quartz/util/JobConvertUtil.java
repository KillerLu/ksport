package com.killer.ksport.quartz.util;

import com.killer.ksport.common.core.controller.BaseController;
import com.killer.ksport.common.core.db.view.quartz.JobEntity;
import com.killer.ksport.quartz.job.DynamicJob;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ：Killer
 * @date ：Created in 19-7-18 下午3:34
 * @description：${description}
 * @modified By：
 * @version: version
 */
public class JobConvertUtil {

    protected final static Logger logger = LoggerFactory.getLogger(BaseController.class);


    public static Class getJobRunningClass(JobEntity jobEntity) {
        Class clazz=null;
        if (StringUtils.isNotBlank(jobEntity.getJobClass())) {
            //通过指定执行类的(静态任务调度)
            try {
                clazz = Class.forName(jobEntity.getJobClass());
            } catch (ClassNotFoundException e) {
                logger.error(jobEntity.getJobGroup()+"-"+jobEntity.getJobName()+"没有找到该任务的执行类:"+jobEntity.getJobClass());
            }
        }else{
            //动态任务调度
            clazz=DynamicJob.class;
        }
        return clazz;
    }

    //获取JobDataMap.(Job参数对象)
    public static JobDataMap getJobDataMap(JobEntity job) {
        JobDataMap map = new JobDataMap();
        map.put("name", job.getJobName());
        map.put("group", job.getJobGroup());
        map.put("cronExpression", job.getCron());
        map.put("parameter", job.getParameter());
        map.put("JobDescription", job.getDescription());
        map.put("vmParam", job.getVmParam());
        map.put("jarPath", job.getJarPath());
        return map;
    }

    //获取JobKey,包含Name和Group
    public static JobKey getJobKey(JobEntity job) {
        return JobKey.jobKey(job.getJobName(), job.getJobGroup());
    }

    //获取Trigger,包含Name和Group
    public static Trigger getTrigger(JobEntity job) {
        return TriggerBuilder.newTrigger()
                .withIdentity(job.getJobName(), job.getJobGroup())
                .withSchedule(CronScheduleBuilder.cronSchedule(job.getCron()))
                .build();
    }
}
