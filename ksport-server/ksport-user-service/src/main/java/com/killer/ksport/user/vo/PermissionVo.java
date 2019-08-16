package com.killer.ksport.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author ：Killer
 * @date ：Created in 19-7-9 下午3:19
 * @description：${description}
 * @modified By：
 * @version: version
 */
@ApiModel("权限")
public class PermissionVo {

    @ApiModelProperty("权限id")
    private Long id;

    @ApiModelProperty("模块id")
    private Long moduleId;

    @ApiModelProperty("权限名称")
    private String name;

    @ApiModelProperty("权限code")
    private String code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
