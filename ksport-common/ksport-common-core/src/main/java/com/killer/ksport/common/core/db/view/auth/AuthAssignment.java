package com.killer.ksport.common.core.db.view.auth;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 *
 * @author killer
 * @date 2019-08-08
 */
@TableName("t_auth_assignment")
public class AuthAssignment extends Model<AuthAssignment> {

    private static final long serialVersionUID = 1L;


    //重写这个方法，return当前类的主键
    @Override
    protected Serializable pkVal() {
        return id;
    }


    /**
     * 主键id
     */
    @TableId
    private Long id;
    /**
     * 资源访问路径
     */
    private String resourcePath;
    /**
     * 权限code
     */
    private String permissionCode;
    /**
     * 删除标志 0:未删除 1:已删除
     */
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Boolean deleted;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;


    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return this.id;
    }


    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }
    public String getResourcePath() {
        return this.resourcePath;
    }


    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }
    public String getPermissionCode() {
        return this.permissionCode;
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
