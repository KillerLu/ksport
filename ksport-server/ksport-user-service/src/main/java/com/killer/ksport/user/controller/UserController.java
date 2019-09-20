package com.killer.ksport.user.controller;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.killer.ksport.common.core.controller.BaseController;
import com.killer.ksport.common.core.db.view.ksport.Permission;
import com.killer.ksport.common.core.db.view.ksport.Role;
import com.killer.ksport.common.core.db.view.ksport.UserAccount;
import com.killer.ksport.common.core.db.view.ksport.UserInfo;
import com.killer.ksport.common.core.enums.StatusEnum;
import com.killer.ksport.common.core.exception.CommonException;
import com.killer.ksport.common.core.controller.ResponseBuilder;
import com.killer.ksport.common.core.util.CloneUtil;
import com.killer.ksport.common.security.util.MD5Util;
import com.killer.ksport.token.model.LoginUser;
import com.killer.ksport.user.service.IModuleService;
import com.killer.ksport.user.service.IRoleService;
import com.killer.ksport.user.service.IUserService;
import com.killer.ksport.user.vo.UserAccountVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ：Killer
 * @date ：Created in 19-7-3 下午2:53
 * @description：${description}
 * @modified By：
 * @version: version
 */
@RestController
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IModuleService moduleService;

    @ApiOperation(value = "新增账户", httpMethod = "POST", notes = "新增账户")
    @RequestMapping("/addAccount")
    public Object addUserAcount(@Valid UserAccountVo userAccountVo, BindingResult result){
        ResponseBuilder builder = new ResponseBuilder();
        Map<String, String> fieldErrorMap = getErrors(result);
        if (fieldErrorMap.size() > 0)
        {
            builder.error().message(getErrorsString(result)).add("fields", fieldErrorMap);
            return builder.build();
        }
        userService.saveUserAccount(userAccountVo);
        return builder.success().build();
    }

    @ApiOperation(value = "查询用户列表", httpMethod = "GET", notes = "查询用户列表",response = UserAccountVo.class)
    @RequestMapping("/listUser")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码, 默认为1, 如果page < 0, 则返回所有信息", required = true, dataType = "int64", paramType = "query"),
            @ApiImplicitParam(name = "pageLength", value = "每页记录数, 默认为10", required = true, dataType = "integer", paramType = "query")
    })
    public Object listUser(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                        @RequestParam(value = "pageLength", defaultValue = "20") Integer pageLength){
        ResponseBuilder builder = new ResponseBuilder();
        //查询用户
        List<UserAccountVo> userAccountVos = userService.listUser(page,pageLength);
        //查询总用户人数
        int count=userService.count();
        return builder.success().data(userAccountVos).add("total", count).build();
    }

    @ApiOperation(value = "编辑用户(更改,禁用,启用,但不包括删除)", httpMethod = "POST", notes = "编辑用户(更改,禁用,启用)")
    @RequestMapping("/modifyUser")
    public Object modifyUser(UserAccountVo userAccountVo) {
        if (userAccountVo.getId()==null) {
            throw new CommonException("请选择用户");
        }
        userService.updateUser(userAccountVo);
        return new ResponseBuilder().success().build();
    }

    @ApiOperation(value = "删除用户", httpMethod = "POST", notes = "删除用户")
    @RequestMapping("/deleteUser")
    public Object deleteUser(Long id) {
        userService.deleteUser(id);
        return new ResponseBuilder().success().build();
    }


    @ApiOperation(value = "根据用户名和密码查询用户", httpMethod = "GET", notes = "根据用户名和密码查询用户",response = UserAccountVo.class)
    @RequestMapping("/getLoginUser")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "用户名", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "string", paramType = "query")
    })
    public Object getLoginUser(@RequestParam(value = "account") String account,
                           @RequestParam(value = "password") String password){
        ResponseBuilder builder = new ResponseBuilder();
        UserAccount userAccount = userService.getUserAccountByAccount(account);
        if (userAccount == null) {
            return builder.error().message("该用户不存在").build();
        }
        if (!MD5Util.verify(password, userAccount.getPassword())) {
            return builder.error().message("密码错误").build();
        }
        //构建登录用户信息
        LoginUser loginUser = new LoginUser(userAccount.getUserId(), userAccount.getAccount(), StatusEnum.NORMAL.getValue().equals(userAccount.getStatus()));
        //查询该用户下的角色
        List<Role> roles = roleService.listRoleByUser(userAccount.getUserId());
        if (CollectionUtils.isNotEmpty(roles)) {
            loginUser.setRoles(roles.stream().map(p -> p.getName()).collect(Collectors.toList()));
        }
        //查询该用户下的权限
        List<Permission> permissions = moduleService.listPermissionByUser(userAccount.getUserId());
        if (CollectionUtils.isNotEmpty(permissions)) {
            loginUser.setPermissions(permissions.stream().map(p->p.getCode()).collect(Collectors.toList()));
        }
        return builder.success().data(loginUser).build();
    }

    @ApiOperation(value = "查询某用户是否存在", httpMethod = "GET", notes = "查询某用户是否存在",response = Boolean.class)
    @RequestMapping("/checkUser")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "int64", paramType = "query")
    })
    public Object checkUser(@RequestParam(value = "id") Long id){
        ResponseBuilder builder = new ResponseBuilder();
        try {
            UserInfo userInfo=userService.getById(id);
            //若该用户存在则返回成功
            return builder.success().data(userInfo!=null?true:false).build();
        } catch (Exception e) {
            return builder.error().build();
        }
    }

}
