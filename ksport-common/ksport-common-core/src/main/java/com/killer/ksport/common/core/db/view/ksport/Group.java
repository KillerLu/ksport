package com.killer.ksport.common.core.db.view.ksport;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * 群组(班级,年级,学校)表
 *
 * @author killer
 * @date 2019-08-20
 */
@TableName("t_group")
public class Group extends Model<Group> {

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
     * 上级群组类型
     */
    private Long parentId;
    /**
     * 群组名称
     */
    private String name;
    /**
     * 群组类型  1:学校,2:年级,3:班级
     */
    private Integer type;
    /**
     * 入学年份(用于计算多少年级)
     */
    private Integer enrollYear;
    /**
     * 班级序号(如1年1班,parent_id和number作为联合唯一约束)
     */
    private Integer number;
    /**
     * 删除标记 0:未删除,1:已删除
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


    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    public Long getParentId() {
        return this.parentId;
    }


    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }


    public void setType(Integer type) {
        this.type = type;
    }
    public Integer getType() {
        return this.type;
    }


    public void setEnrollYear(Integer enrollYear) {
        this.enrollYear = enrollYear;
    }
    public Integer getEnrollYear() {
        return this.enrollYear;
    }


    public void setNumber(Integer number) {
        this.number = number;
    }
    public Integer getNumber() {
        return this.number;
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
