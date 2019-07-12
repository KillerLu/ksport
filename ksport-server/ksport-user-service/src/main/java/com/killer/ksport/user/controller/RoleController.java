package com.killer.ksport.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.killer.ksport.common.core.constant.PageConstant;
import com.killer.ksport.common.core.controller.BaseController;
import com.killer.ksport.common.core.db.view.ksport.Role;
import com.killer.ksport.common.core.db.view.ksport.UserAccount;
import com.killer.ksport.common.core.util.CloneUtils;
import com.killer.ksport.common.core.web.ResponseBuilder;
import com.killer.ksport.user.service.IRoleService;
import com.killer.ksport.user.service.impl.RoleService;
import com.killer.ksport.user.vo.RoleVo;
import com.killer.ksport.user.vo.UserAccountVo;
import com.killer.ksport.user.vo.UserRoleVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
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
        Role role = CloneUtils.clone(roleVo, Role.class);
        roleService.save(role);
        return new ResponseBuilder().success().build();
    }

    @ApiOperation(value = "分配角色", httpMethod = "POST", notes = "分配角色")
    @RequestMapping("/assignRole")
    public Object assignRole(UserRoleVo userRoleVo) {
        roleService.assignRole(userRoleVo);
        return new ResponseBuilder().success().build();
    }

    @ApiOperation(value = "查询角色列表", httpMethod = "POST", notes = "查询角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "查询第几页", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageLength", value = "每页查询多少条", required = false, dataType = "int", paramType = "query")})
    @RequestMapping("/listRole")
    public Object listRole(@RequestParam(value = "page", defaultValue = PageConstant.PAGE_NUMBER+"") Integer page,
                           @RequestParam(value = "pageLength", defaultValue = PageConstant.PAGE_LENGTH + "") Integer pageLength) {
        IPage<Role> data=roleService.page(new Page<Role>(page, pageLength), new QueryWrapper<Role>());
        return new ResponseBuilder().success().data(data).build();
    }
}
