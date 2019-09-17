package com.killer.ksport.common.core.enums;

/**
 * @author ：Killer
 * @date ：Created in 19-9-16 下午2:28
 * @description：${description}
 * @modified By：
 * @version: version
 */
public enum  RequestTypeEnum {

    GET("GET"),
    POST("POST");

    private String value;

    RequestTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
