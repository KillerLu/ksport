package com.killer.ksport.user.controller;

import com.killer.ksport.common.core.controller.BaseController;
import com.killer.ksport.common.core.db.view.ksport.Role;
import com.killer.ksport.common.core.db.view.ksport.UserAccount;
import com.killer.ksport.common.core.db.view.ksport.UserInfo;
import com.killer.ksport.common.core.util.CloneUtils;
import com.killer.ksport.common.core.web.ResponseBuilder;
import com.killer.ksport.common.security.model.view.LoginUser;
import com.killer.ksport.user.service.IUserService;
import com.killer.ksport.user.service.impl.UserService;
import com.killer.ksport.user.vo.UserAccountVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ：Killer
 * @date ：Created in 19-7-3 下午2:53
 * @description：${description}
 * @modified By：
 * @version: version
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {



    @Autowired
    private IUserService userService;

    @ApiOperation(value = "新增账户", httpMethod = "POST", notes = "新增账户")
    @RequestMapping("/addAccount")
    public Object addUserAcount(UserAccountVo userAccountVo){
        userService.saveUserAccount(userAccountVo);
        return new ResponseBuilder().success().build();
    }

    /**
     *  远程调用接口
     */
    @RequestMapping("/getLoginUser")
    public LoginUser getLoginUser(String account, String password) {
        return userService.getLoginUser(account, password);
    }

}
