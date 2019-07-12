package com.killer.ksport.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author ：Killer
 * @date ：Created in 19-7-9 下午2:44
 * @description：${description}
 * @modified By：
 * @version: version
 */
@ApiModel("角色")
public class RoleVo {

    @ApiModelProperty("角色id")
    private Long id;

    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("角色备注")
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
