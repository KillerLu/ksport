package com.killer.ksport.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author ：Killer
 * @date ：Created in 19-7-9 下午3:05
 * @description：${description}
 * @modified By：
 * @version: version
 */
@ApiModel("模块")
public class ModuleVo {

    @ApiModelProperty("模块id")
    private Long id;

    @ApiModelProperty("模块名称")
    private String name;

    @ApiModelProperty("模块图标")
    private String logo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
