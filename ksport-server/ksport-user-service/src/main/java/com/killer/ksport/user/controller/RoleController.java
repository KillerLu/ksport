package com.killer.ksport.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.killer.ksport.common.core.constant.PageConstant;
import com.killer.ksport.common.core.controller.BaseController;
import com.killer.ksport.common.core.db.view.ksport.Role;
import com.killer.ksport.common.core.exception.CommonException;
import com.killer.ksport.common.core.util.CloneUtil;
import com.killer.ksport.common.core.controller.ResponseBuilder;
import com.killer.ksport.user.service.IRoleService;
import com.killer.ksport.user.vo.RoleVo;
import com.killer.ksport.user.vo.UserRoleVo;
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
 * @date ：Created in 19-7-9 下午2:23
 * @description：${description}
 * @modified By：
 * @version: version
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Autowired
    private IRoleService roleService;

    @ApiOperation(value = "新增角色", httpMethod = "POST", notes = "新增角色")
    @RequestMapping("/addRole")
    public Object addRole(RoleVo roleVo){
        Role role = CloneUtil.clone(roleVo, Role.class);
        roleService.save(role);
        return new ResponseBuilder().success().build();
    }

    @ApiOperation(value = "分配角色", httpMethod = "POST", notes = "分配角色")
    @RequestMapping("/assignRole")
    public Object assignRole(UserRoleVo userRoleVo) {
        roleService.assignRole(userRoleVo);
        return new ResponseBuilder().success().build();
    }

    @ApiOperation(value = "查询角色列表", httpMethod = "GET", notes = "查询角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "查询第几页", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageLength", value = "每页查询多少条", required = false, dataType = "int", paramType = "query")})
    @RequestMapping("/listRole")
    public Object listRole(@RequestParam(value = "page", defaultValue = PageConstant.PAGE_NUMBER+"") Integer page,
                           @RequestParam(value = "pageLength", defaultValue = PageConstant.PAGE_LENGTH + "") Integer pageLength) {
        IPage data=roleService.page(new Page<Role>(page, pageLength), new QueryWrapper<Role>());
        List<RoleVo> roleVos = CloneUtil.batchClone(data.getRecords(), RoleVo.class);
        return new ResponseBuilder().success().data(roleVos).add("total", data.getTotal()).build();
    }


    @ApiOperation(value = "查询某个用户的所有角色", httpMethod = "GET", notes = "查询某个用户的所有角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "long", paramType = "query")})
    @RequestMapping("/listRoleByUser")
    public Object listRoleByUser(@RequestParam(value = "userId") Long userId) {
        List<Role> roles = roleService.listRoleByUser(userId);
        List<RoleVo> roleVos = CloneUtil.batchClone(roles, RoleVo.class);
        return new ResponseBuilder().success().data(roleVos).build();
    }


    @ApiOperation(value = "修改角色", httpMethod = "POST", notes = "修改角色")
    @RequestMapping("/modifyRole")
    public Object modifyRole(RoleVo roleVo){
        if (roleVo.getId() == null) {
            throw new CommonException("请选择角色");
        }
        Role oldRole = roleService.findExistById(roleVo.getId());
        CloneUtil.copyPropertiesIgnoreNull(roleVo, oldRole);
        roleService.updateById(oldRole);
        return new ResponseBuilder().success().build();
    }

    @ApiOperation(value = "删除角色", httpMethod = "POST", notes = "删除角色")
    @RequestMapping("/deleteRole")
    public Object deleteRole(Long id){
        roleService.deleteRole(id);
        return new ResponseBuilder().success().build();
    }

}
