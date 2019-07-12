package com.killer.ksport.common.core.exception;

/**
 * @author ：Killer
 * @date ：Created in 19-7-4 下午5:28
 * @description：${description}
 * @modified By：
 * @version: version
 */
public class CommonException extends RuntimeException {

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public CommonException() {
    }

    public CommonException(String message,int code) {
        super(message);
        this.code = code;
    }

    public CommonException(String message) {
        super(message);
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonException(Throwable cause) {
        super(cause);
    }

    public CommonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
