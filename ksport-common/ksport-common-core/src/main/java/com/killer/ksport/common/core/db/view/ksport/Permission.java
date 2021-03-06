package com.killer.ksport.common.core.db.view.ksport;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限表
 *
 * @author killer
 * @date 2019-07-09
 */
@TableName("t_permission")
public class Permission extends Model<Permission> {

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
     * 模块id
     */
    private Long moduleId;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限编码
     */
    private String code;
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


    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }
    public Long getModuleId() {
        return this.moduleId;
    }


    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }


    public void setCode(String code) {
        this.code = code;
    }
    public String getCode() {
        return this.code;
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
