package com.killer.ksport.auth.vo;

/**
 * @author ：Killer
 * @date ：Created in 19-8-15 下午3:40
 * @description：${description}
 * @modified By：
 * @version: version
 */
public class VcodeTime {

    /**
     * 验证码
     */
    private String verifyCode;

    /**
     * 上次请求验证码时间
     */
    private long requestTime;

    public VcodeTime(String verifyCode, long requestTime) {
        this.verifyCode = verifyCode;
        this.requestTime = requestTime;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(long requestTime) {
        this.requestTime = requestTime;
    }
}
