package com.killer.ksport.quartz.controller;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.killer.ksport.common.core.controller.BaseController;
import com.killer.ksport.common.core.db.view.quartz.JobEntity;
import com.killer.ksport.common.core.util.CloneUtils;
import com.killer.ksport.common.core.web.ResponseBuilder;
import com.killer.ksport.quartz.service.IJobEntityService;
import com.killer.ksport.quartz.util.JobConvertUtil;
import com.killer.ksport.quartz.vo.JobEntityVo;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

/**
 * @author ：Killer
 * @date ：Created in 19-7-17 下午5:43
 * @description：${description}
 * @modified By：
 * @version: version
 */
@RestController
@RequestMapping("/job")
public class JobController extends BaseController {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    @Autowired
    private IJobEntityService jobEntityService;

    //初始化启动所有的Job
    @PostConstruct
    public void initialize() {
        try {
            reStartAllJobs();
            logger.info("INIT SUCCESS");
        } catch (SchedulerException e) {
            logger.info("INIT EXCEPTION : " + e.getMessage());
        }
    }

    @ApiOperation(value = "新增job", httpMethod = "POST", notes = "新增job")
    @RequestMapping("/addJob")
    public Object addJob(JobEntityVo jobEntityVo) throws SchedulerException {
        JobEntity jobEntity = CloneUtils.clone(jobEntityVo, JobEntity.class);
        jobEntityService.scheduleJob(jobEntity);
        return new ResponseBuilder().success().build();
    }

    @ApiOperation(value = "暂停job", httpMethod = "POST", notes = "暂停job")
    @RequestMapping("/pauseJob")
    public Object pauseJob(Long id) throws SchedulerException {
        jobEntityService.pauseJob(id);
        return new ResponseBuilder().success().build();
    }

    @ApiOperation(value = "继续job", httpMethod = "POST", notes = "继续job")
    @RequestMapping("/resumeJob")
    public Object resumeJob(Long id) throws SchedulerException {
        jobEntityService.resumeJob(id);
        return new ResponseBuilder().success().build();
    }

    @ApiOperation(value = "删除job", httpMethod = "POST", notes = "删除job")
    @RequestMapping("/deleteJob")
    public Object deleteJob(Long id) throws SchedulerException {
        jobEntityService.deleteJob(id);
        return new ResponseBuilder().success().build();
    }

    @ApiOperation(value = "更新job", httpMethod = "POST", notes = "更新job")
    @RequestMapping("/updateJob")
    public Object updateJob(JobEntityVo jobEntityVo) throws SchedulerException {
        JobEntity jobEntity = CloneUtils.clone(jobEntityVo, JobEntity.class);
        jobEntityService.modifyJob(jobEntity);
        return new ResponseBuilder().success().build();
    }


    @ApiOperation(value = "重启数据库中所有的Job", httpMethod = "GET", notes = "重启数据库中所有的Job")
    @RequestMapping("/refresh")
    public Object refreshAll() throws SchedulerException {
        reStartAllJobs();
        return new ResponseBuilder().success().build();
    }

    @ApiOperation(value = "立即执行某个job", httpMethod = "GET", notes = "立即执行某个job")
    @RequestMapping("/triggerJob")
    public Object triggerJob(Long id) throws SchedulerException {
        jobEntityService.triggerJob(id);
        return new ResponseBuilder().success().build();
    }

    /**
     * 重新启动所有的job
     */
    private void reStartAllJobs() throws SchedulerException {
        //只允许一个线程进入操作
        synchronized (logger) {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            Set<JobKey> set = scheduler.getJobKeys(GroupMatcher.anyGroup());
            //暂停所有JOB
            scheduler.pauseJobs(GroupMatcher.anyGroup());
            for (JobKey jobKey : set) {
                //删除从数据库中注册的所有JOB
                scheduler.unscheduleJob(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup()));
                scheduler.deleteJob(jobKey);
            }
            List<JobEntity> jobEntityList = jobEntityService.list();
            if (CollectionUtils.isEmpty(jobEntityList)) {
                return;
            }
            for (JobEntity job : jobEntityList) {
                //从数据库中注册的所有JOB
                logger.info("Job register name : {} , group : {} , cron : {}", job.getJobName(), job.getJobGroup(), job.getCron());
                jobEntityService.scheduleJob(job);
            }
        }
    }
}
