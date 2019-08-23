package com.killer.ksport.common.core.db.view.ksport;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * 群组用户关联关系
 *
 * @author killer
 * @date 2019-08-16
 */
@TableName("t_group_user")
public class GroupUser extends Model<GroupUser> {

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
     * 用户id
     */
    private Long userId;
    /**
     * 群组id
     */
    private Long groupId;
    /**
     * 是否班主任标志 1:是,0:否
     */
    private Boolean headTeacher;
    /**
     * 删除标志 0:未删除,1:已删除
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


    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getUserId() {
        return this.userId;
    }


    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
    public Long getGroupId() {
        return this.groupId;
    }


    public void setHeadTeacher(Boolean headTeacher) {
        this.headTeacher = headTeacher;
    }
    public Boolean isHeadTeacher() {
        return this.headTeacher;
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
