package com.killer.ksport.common.core.db.view.quartz;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务实体
 *
 * @author killer
 * @date 2019-07-18
 */
@TableName("t_job_entity")
public class JobEntity extends Model<JobEntity> {

    private static final long serialVersionUID = 1L;


    //重写这个方法，return当前类的主键
    @Override
    protected Serializable pkVal() {
        return id;
    }


    /**
     * 主键id
     */
    @TableId
    private Long id;
    /**
     * job名称
     */
    private String jobName;
    /**
     * job组名
     */
    private String jobGroup;
    /**
     * 执行的cron(表达式)
     */
    private String cron;
    /**
     * job的参数
     */
    private String parameter;
    /**
     * vm参数
     */
    private String vmParam;
    /**
     * 执行该job的全类名
     */
    private String jobClass;
    /**
     * job的描述
     */
    private String description;
    /**
     * job的jar路径,在这里指的是定时执行一些可执行的jar包
     */
    private String jarPath;
    /**
     * 删除标记 1:已删除 0:未删除
     */
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Boolean deleted;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;


    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return this.id;
    }


    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    public String getJobName() {
        return this.jobName;
    }


    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }
    public String getJobGroup() {
        return this.jobGroup;
    }


    public void setCron(String cron) {
        this.cron = cron;
    }
    public String getCron() {
        return this.cron;
    }


    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
    public String getParameter() {
        return this.parameter;
    }


    public void setVmParam(String vmParam) {
        this.vmParam = vmParam;
    }
    public String getVmParam() {
        return this.vmParam;
    }


    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }
    public String getJobClass() {
        return this.jobClass;
    }


    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return this.description;
    }


    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }
    public String getJarPath() {
        return this.jarPath;
    }


    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
    public Boolean isDeleted() {
        return this.deleted;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getCreateTime() {
        return this.createTime;
    }


    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
    public Date getModifyTime() {
        return this.modifyTime;
    }

}
