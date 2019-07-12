package com.killer.ksport.user.controller;

import com.killer.ksport.common.core.controller.BaseController;
import com.killer.ksport.common.core.db.view.ksport.Module;
import com.killer.ksport.common.core.db.view.ksport.Permission;
import com.killer.ksport.common.core.util.CloneUtils;
import com.killer.ksport.common.core.web.ResponseBuilder;
import com.killer.ksport.user.service.IModuleService;
import com.killer.ksport.user.vo.ModuleVo;
import com.killer.ksport.user.vo.PermissionVo;
import com.killer.ksport.user.vo.RolePermissionVo;
import com.killer.ksport.user.vo.RoleVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ：Killer
 * @date ：Created in 19-7-9 下午2:22
 * @description：${description}
 * @modified By：
 * @version: version
 */
@RestController
@RequestMapping("/module")
public class ModuleController extends BaseController {

    @Autowired
    private IModuleService moduleService;

    @ApiOperation(value = "新增模块", httpMethod = "POST", notes = "新增模块")
    @RequestMapping("/addModule")
    public Object addModule(ModuleVo moduleVo){
        Module module = CloneUtils.clone(moduleVo, Module.class);
        moduleService.save(module);
        return new ResponseBuilder().success().build();
    }

    @ApiOperation(value = "新增权限", httpMethod = "POST", notes = "新增权限")
    @RequestMapping("/addPermission")
    public Object addPermission(PermissionVo permissionVo){
        moduleService.savePermission(permissionVo);
        return new ResponseBuilder().success().build();
    }

    @ApiOperation(value = "分配权限", httpMethod = "POST", notes = "分配权限")
    @RequestMapping("/assignPermission")
    public Object assignPermission(RolePermissionVo rolePermissionVo){
        moduleService.assignPermission(rolePermissionVo);
        return new ResponseBuilder().success().build();
    }

}
