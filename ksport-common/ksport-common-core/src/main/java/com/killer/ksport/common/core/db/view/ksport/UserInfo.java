package com.killer.ksport.common.core.db.view.ksport;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息表
 *
 * @author killer
 * @date 2019-07-03
 */
@TableName("t_user_info")
public class UserInfo extends Model<UserInfo> {

    private static final long serialVersionUID = 1L;


    //重写这个方法，return当前类的主键
    @Override
    protected Serializable pkVal() {
        return id;
    }


    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 用户名称
     */
    private String name;
    /**
     * 1:男 2:女
     */
    private String sex;
    /**
     * 出生日期
     */
    private Date birthday;
    /**
     * 电话
     */
    private String mobile;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 1：正常 2:已删除
     */
    private String isDelete;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最新修改时间
     */
    private Date modifyTime;


    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return this.id;
    }


    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }


    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getSex() {
        return this.sex;
    }


    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public Date getBirthday() {
        return this.birthday;
    }


    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getMobile() {
        return this.mobile;
    }


    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getAvatar() {
        return this.avatar;
    }


    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }
    public String getIsDelete() {
        return this.isDelete;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getCreateTime() {
        return this.createTime;
    }


    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
    public Date getModifyTime() {
        return this.modifyTime;
    }

}
