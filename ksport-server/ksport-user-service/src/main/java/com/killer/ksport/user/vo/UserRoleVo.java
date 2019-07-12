package com.killer.ksport.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author ：Killer
 * @date ：Created in 19-7-9 下午2:52
 * @description：${description}
 * @modified By：
 * @version: version
 */
@ApiModel("用户角色关系")
public class UserRoleVo {

    @ApiModelProperty("用户id")
    @NotNull
    private Long userId;

    @ApiModelProperty("角色id列表")
    private List<Long> roleIds;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }
}
