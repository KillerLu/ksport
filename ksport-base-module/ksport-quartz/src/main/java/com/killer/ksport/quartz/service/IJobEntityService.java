package com.killer.ksport.quartz.service;

import com.killer.ksport.common.core.db.view.quartz.JobEntity;
import com.killer.ksport.common.core.service.IBaseService;
import org.quartz.*;

/**
 * @author ：Killer
 * @date ：Created in 19-7-17 下午5:54
 * @description：${description}
 * @modified By：
 * @version: version
 */
public interface IJobEntityService extends IBaseService<JobEntity>{

    /**
     * 向调度中心注册任务
     */
    void scheduleJob(JobEntity job) throws SchedulerException;

    /**
     * 暂停Job
     */
    void pauseJob(Long id) throws SchedulerException;

    /**
     * 恢复Job
     */
    void resumeJob(Long id) throws SchedulerException;

    /**
     * 删除Job
     */
    void deleteJob(Long id) throws SchedulerException;

    /**
     * 更新Job
     */
    void modifyJob(JobEntity jobEntity) throws SchedulerException;

    /**
     * 触发job
     */
    void triggerJob(Long id) throws SchedulerException;
}
