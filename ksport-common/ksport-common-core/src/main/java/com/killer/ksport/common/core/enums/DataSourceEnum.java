package com.killer.ksport.common.core.enums;

/**
 * @author ：Killer
 * @date ：Created in 19-7-16 下午3:04
 * @description：${description}
 * @modified By：
 * @version: version
 */
public enum  DataSourceEnum {

    /**
     * 授权系统数据库
     */
    AUTH("auth"),

    /**
     * 任务调度数据库
     */
    QUARTZ("quartz"),

    /**
     * 业务数据库
     */
    KSPORT("ksport");

    private String value;

    DataSourceEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
