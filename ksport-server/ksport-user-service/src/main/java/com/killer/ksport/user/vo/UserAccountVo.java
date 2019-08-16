package com.killer.ksport.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author ：Killer
 * @date ：Created in 19-7-3 下午3:11
 * @description：${description}
 * @modified By：
 * @version: version
 */
@ApiModel("用户账户")
public class UserAccountVo {

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("账户名")
    @NotNull(message = "用户名不能为空")
    private String account;

    @ApiModelProperty("密码")
    @JsonIgnore
    @NotNull(message = "密码不能为空")
    private String password;

    @ApiModelProperty("姓名")
    @NotNull(message = "姓名不能为空")
    private String name;

    @ApiModelProperty("性别 1:男,2:女")
    @NotNull(message = "性别不能为空")
    private String sex;

    @ApiModelProperty("出生日期")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;

    @ApiModelProperty("电话")
    private String mobile;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("账号状态")
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
