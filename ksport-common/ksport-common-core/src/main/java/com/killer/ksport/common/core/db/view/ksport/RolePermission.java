package com.killer.ksport.common.core.db.view.ksport;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色权限关联表
 *
 * @author killer
 * @date 2019-07-09
 */
@TableName("t_role_permission")
public class RolePermission extends Model<RolePermission> {

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
     * 角色id
     */
    private Long roleId;
    /**
     * 权限id
     */
    private Long permissionId;
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
     * 修改时间
     */
    private Date modifyTime;


    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return this.id;
    }


    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
    public Long getRoleId() {
        return this.roleId;
    }


    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }
    public Long getPermissionId() {
        return this.permissionId;
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
