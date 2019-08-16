package com.killer.ksport.quartz.service.impl;

import com.killer.ksport.common.core.db.dao.quartz.JobEntityDao;
import com.killer.ksport.common.core.db.view.quartz.JobEntity;
import com.killer.ksport.common.core.service.impl.BaseService;
import com.killer.ksport.quartz.service.IJobEntityService;
import com.killer.ksport.quartz.util.JobConvertUtil;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：Killer
 * @date ：Created in 19-7-17 下午5:55
 * @description：${description}
 * @modified By：
 * @version: version
 */
@Service
public class JobEntityService extends BaseService<JobEntityDao, JobEntity> implements IJobEntityService {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    public JobDetail geJobDetail(JobEntity job) {

        return JobBuilder.newJob(JobConvertUtil.getJobRunningClass(job))
                .withIdentity(JobConvertUtil.getJobKey(job))
                .withDescription(job.getDescription())
                .setJobData(JobConvertUtil.getJobDataMap(job))
                .storeDurably()
                .build();
    }


    @Override
    public void scheduleJob(JobEntity job) throws SchedulerException {
        JobDetail jobDetail = geJobDetail(job);
        if (jobDetail != null) {
            schedulerFactoryBean.getScheduler().scheduleJob(jobDetail, JobConvertUtil.getTrigger(job));
        }
        //保存该job(新增是job的id是null,而重启时job的id不为null
        if (job.getId() == null) {
            super.save(job);
        }
    }

    @Override
    public void pauseJob(Long id) throws SchedulerException {
        schedulerFactoryBean.getScheduler().pauseJob(JobConvertUtil.getJobKey(super.findExistById(id)));
    }


    @Override
    public void resumeJob(Long id) throws SchedulerException {
        schedulerFactoryBean.getScheduler().resumeJob(JobConvertUtil.getJobKey(super.findExistById(id)));
    }


    @Override
    @Transactional
    public void deleteJob(Long id) throws SchedulerException {
        //从调度器移除该job
        schedulerFactoryBean.getScheduler().deleteJob(JobConvertUtil.getJobKey(super.findExistById(id)));
        //同时删除该jobEntity记录
        super.removeById(id);
    }


    @Override
    @Transactional
    public void modifyJob(JobEntity jobEntity) throws SchedulerException {
        if (jobEntity == null||jobEntity.getId()==null) {
            return;
        }
        //从调度器移除该job
        schedulerFactoryBean.getScheduler().deleteJob(JobConvertUtil.getJobKey(super.findExistById(jobEntity.getId())));
        //重新注册新的job
        schedulerFactoryBean.getScheduler().scheduleJob(geJobDetail(jobEntity), JobConvertUtil.getTrigger(jobEntity));
        //同时更新该jobEntity记录
        super.updateById(jobEntity);
    }

    @Override
    public void triggerJob(Long id) throws SchedulerException {
        JobEntity jobEntity = super.findExistById(id);
        schedulerFactoryBean.getScheduler().triggerJob(JobConvertUtil.getJobKey(jobEntity),JobConvertUtil.getJobDataMap(jobEntity));
    }

}
