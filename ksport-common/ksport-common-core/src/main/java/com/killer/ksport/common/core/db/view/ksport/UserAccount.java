package com.killer.ksport.common.core.db.view.ksport;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户账号表
 *
 * @author killer
 * @date 2019-07-04
 */
@TableName("t_user_account")
public class UserAccount extends Model<UserAccount> {

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
     * 用户id
     */
    private Long userId;
    /**
     * 账户名
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * 账号状态
     */
    @TableField(fill = FieldFill.INSERT)
    private String status;
    /**
     * 0:未删除 1:已删除
     */
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Boolean deleted;
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


    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getUserId() {
        return this.userId;
    }


    public void setAccount(String account) {
        this.account = account;
    }
    public String getAccount() {
        return this.account;
    }


    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return this.password;
    }


    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return this.status;
    }


    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
    public Boolean isDeleted() {
        return this.deleted;
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
