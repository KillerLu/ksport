package com.killer.ksport.common.core.db.view.ksport;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件表
 *
 * @author killer
 * @date 2019-07-19
 */
@TableName("t_file")
public class File extends Model<File> {

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
     * 文件标识id
     */
    private String uuid;
    /**
     * 文件名称
     */
    private String name;
    /**
     * 文件路径
     */
    private String path;
    /**
     * 删除标识 0:未删除 1:已删除
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


    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getUuid() {
        return this.uuid;
    }


    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }


    public void setPath(String path) {
        this.path = path;
    }
    public String getPath() {
        return this.path;
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
