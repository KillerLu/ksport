package com.killer.ksport.group.vo;

import com.killer.ksport.common.core.db.view.ksport.Group;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author ：Killer
 * @date ：Created in 19-8-16 下午4:18
 * @description：${description}
 * @modified By：
 * @version: version
 */
@ApiModel
public class GroupVo {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("上级群组id")
    private Long parentId;

    @ApiModelProperty("群组名称")
    private String name;

    @ApiModelProperty("群组类型 1:学校,2:年级,3:班级")
    @NotNull(message = "请选择群组类型")
    private Integer type;

    @ApiModelProperty("入学年份(用于计算多少年级)")
    @NotNull(message = "入学年份不能为空")
    private Integer enrollYear;

    @ApiModelProperty("班级序号(如1年1班,parent_id和index作为联合唯一约束)")
    private Integer number;

    @ApiModelProperty("下级群组")
    private List<GroupVo> groups;

    @ApiModelProperty("是否删除标志 0:未删除 1:已删除")
    private Boolean deleted;

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public List<GroupVo> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupVo> groups) {
        this.groups = groups;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getEnrollYear() {
        return enrollYear;
    }

    public void setEnrollYear(Integer enrollYear) {
        this.enrollYear = enrollYear;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
