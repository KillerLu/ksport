package com.killer.ksport.auth.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author ：Killer
 * @date ：Created in 19-8-8 上午9:52
 * @description：${description}
 * @modified By：
 * @version: version
 */
@ApiModel("资源权限分配")
public class AuthAssignmentVo {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("资源访问路径")
    private String resourcePath;

    @ApiModelProperty("权限code")
    private String permissionCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }
}
