package com.killer.ksport.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author ：Killer
 * @date ：Created in 19-7-9 下午3:46
 * @description：${description}
 * @modified By：
 * @version: version
 */
@ApiModel("角色权限关系")
public class RolePermissionVo {

    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("权限id")
    private List<Long> permissionIds;


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public List<Long> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }
}
