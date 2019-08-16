package com.killer.ksport.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author ：Killer
 * @date ：Created in 19-8-15 下午2:09
 * @description：${description}
 * @modified By：
 * @version: version
 */
@ApiModel("登录验证码")
public class VerifyCodeVo {

    @ApiModelProperty("请求id")
    private String reqId;
    @ApiModelProperty("验证码图片base64串")
    private String code;

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
