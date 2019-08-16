package com.killer.ksport.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * @author ：Killer
 * @date ：Created in 19-8-15 下午3:25
 * @description：${description}
 * @modified By：
 * @version: version
 */
@ApiModel
public class LoginUserVo {

    @ApiModelProperty("账号")
    @NotNull(message = "用户名不能为空")
    private String account;

    @NotNull(message = "密码不能为空")
    @ApiModelProperty("密码")
    private String password;

    @NotNull(message = "请求id不能为空")
    @ApiModelProperty("请求id")
    private String reqId;

    @NotNull(message = "验证码不能为空")
    @ApiModelProperty("验证码")
    private String verifyCode;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
