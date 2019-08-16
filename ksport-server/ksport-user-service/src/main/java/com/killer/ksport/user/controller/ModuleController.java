package com.killer.ksport.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.killer.ksport.common.core.constant.PageConstant;
import com.killer.ksport.common.core.controller.BaseController;
import com.killer.ksport.common.core.db.view.ksport.Module;
import com.killer.ksport.common.core.db.view.ksport.Permission;
import com.killer.ksport.common.core.exception.CommonException;
import com.killer.ksport.common.core.util.CloneUtil;
import com.killer.ksport.common.core.controller.ResponseBuilder;
import com.killer.ksport.user.service.IModuleService;
import com.killer.ksport.user.vo.ModuleVo;
import com.killer.ksport.user.vo.PermissionVo;
import com.killer.ksport.user.vo.RolePermissionVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
        Module module = CloneUtil.clone(moduleVo, Module.class);
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

    @ApiOperation(value = "查询模块列表", httpMethod = "GET", notes = "查询模块列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "查询第几页", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageLength", value = "每页查询多少条", required = false, dataType = "int", paramType = "query")})
    @RequestMapping("/listModule")
    public Object listModule(@RequestParam(value = "page", defaultValue = PageConstant.PAGE_NUMBER+"") Integer page,
                           @RequestParam(value = "pageLength", defaultValue = PageConstant.PAGE_LENGTH + "") Integer pageLength) {

        IPage data=moduleService.page(new Page<Module>(page, pageLength), new QueryWrapper<Module>());
        List<ModuleVo> moduleVos = CloneUtil.batchClone(data.getRecords(), ModuleVo.class);
        return new ResponseBuilder().success().data(moduleVos).add("total", data.getTotal()).build();
    }

    @ApiOperation(value = "修改模块", httpMethod = "POST", notes = "修改模块")
    @RequestMapping("/modifyModule")
    public Object modifyModule(ModuleVo moduleVo){
        if (moduleVo.getId() == null) {
            throw new CommonException("请选择对应的模块");
        }
        //查询旧的模块
        Module oldModule = moduleService.findExistById(moduleVo.getId());
        //更新模块
        CloneUtil.copyPropertiesIgnoreNull(moduleVo, oldModule);
        moduleService.updateById(oldModule);
        return new ResponseBuilder().success().build();
    }

    @ApiOperation(value = "删除模块", httpMethod = "POST", notes = "删除模块")
    @RequestMapping("/deleteModule")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "模块id", required = true, dataType = "long", paramType = "query")})
    public Object deleteModule(Long id){
        moduleService.deleteModule(id);
        return new ResponseBuilder().success().build();
    }

    @ApiOperation(value = "查询权限列表", httpMethod = "GET", notes = "查询模块列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "模块id", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "查询第几页", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageLength", value = "每页查询多少条", required = false, dataType = "int", paramType = "query")})
    @RequestMapping("/listPermission")
    public Object listPermission(@RequestParam(value = "moduleId") Long id,
                                 @RequestParam(value = "page", defaultValue = PageConstant.PAGE_NUMBER+"") Integer page,
                                 @RequestParam(value = "pageLength", defaultValue = PageConstant.PAGE_LENGTH + "") Integer pageLength) {
        IPage data = moduleService.listPermissionByModule(id,page,pageLength);
        List<PermissionVo> permissionVos = CloneUtil.batchClone(data.getRecords(), Permission.class);
        return new ResponseBuilder().success().data(permissionVos).add("total", data.getTotal()).build();
    }

    @ApiOperation(value = "修改权限", httpMethod = "POST", notes = "修改权限")
    @RequestMapping("/modifyPermission")
    public Object modifyPermission(PermissionVo permissionVo){
        if (permissionVo.getId() == null) {
            throw new CommonException("请选择对应的权限");
        }
        moduleService.modifyPermission(permissionVo);
        return new ResponseBuilder().success().build();
    }

    @ApiOperation(value = "删除权限", httpMethod = "POST", notes = "删除权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "权限id", required = true, dataType = "long", paramType = "query")})
    @RequestMapping("/deletePermission")
    public Object deletePermission(Long id){
        moduleService.deletePermission(id);
        return new ResponseBuilder().success().build();
    }
}
