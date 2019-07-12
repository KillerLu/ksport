package com.killer.ksport.user.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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

    @ApiModelProperty("账户名")
    private String account;

    @ApiModelProperty("密码")
    @JsonIgnore
    private String password;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("性别 1:男,2:女")
    private String sex;

    @ApiModelProperty("出生日期")
    private Date birthday;

    @ApiModelProperty("电话")
    private String mobile;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date modifyTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
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
