package com.killer.ksport.group.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author ：Killer
 * @date ：Created in 19-8-19 上午9:47
 * @description：${description}
 * @modified By：
 * @version: version
 */
@ApiModel
public class GroupUserVo {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("用户id")
    @NotNull(message = "请指定用户")
    private Long userId;

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("用户性别")
    private String sex;

    @ApiModelProperty("出生日期")
    private Date birthday;

    @ApiModelProperty("用户电话")
    private String mobile;

    @ApiModelProperty("用户头像")
    private String avatar;

    @ApiModelProperty("群组id")
    @NotNull(message = "请指定群组")
    private Long groupId;

    @ApiModelProperty("群组名称")
    private String groupName;

    @ApiModelProperty("是否是班主任")
    private Boolean headTeacher;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Boolean getHeadTeacher() {
        return headTeacher;
    }

    public void setHeadTeacher(Boolean headTeacher) {
        this.headTeacher = headTeacher;
    }
}
