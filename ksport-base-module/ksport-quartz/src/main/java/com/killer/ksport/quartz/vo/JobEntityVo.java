package com.killer.ksport.quartz.vo;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author ：Killer
 * @date ：Created in 19-7-18 上午11:39
 * @description：${description}
 * @modified By：
 * @version: version
 */
@ApiModel
public class JobEntityVo {


    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("job名称")
    private String jobName;

    @ApiModelProperty("job组名")
    private String jobGroup;

    @ApiModelProperty("cron表达式")
    private String cron;

    @ApiModelProperty("运行参数")
    private String parameter;

    @ApiModelProperty("jvm参数")
    private String vmParam;

    @ApiModelProperty("执行全类名,静态调度用")
    private String jobClass;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("job的jar路径,在这里指的是定时执行一些可执行的jar包,动态调度用")
    private String jarPath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getVmParam() {
        return vmParam;
    }

    public void setVmParam(String vmParam) {
        this.vmParam = vmParam;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJarPath() {
        return jarPath;
    }

    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }
}
