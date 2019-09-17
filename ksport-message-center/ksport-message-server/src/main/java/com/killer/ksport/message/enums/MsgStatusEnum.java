package com.killer.ksport.message.enums;


public enum MsgStatusEnum {

    PREPARE(1), CONFIRM(2), ACK(3);

    private int value;

    MsgStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}