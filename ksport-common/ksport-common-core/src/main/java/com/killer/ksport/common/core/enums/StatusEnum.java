package com.killer.ksport.common.core.enums;

/**
 * @author ：Killer
 * @date ：Created in 19-7-4 上午9:50
 * @description：${description}
 * @modified By：
 * @version: version
 */
public enum StatusEnum {
    
    NORMAL("1"),
    FORBIDDEN("2");

    private String value;

    StatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
