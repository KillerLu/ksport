package com.killer.ksport.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.killer.ksport.auth.service.ILoginService;
import com.killer.ksport.common.core.constant.RedisKeyConstant;
import com.killer.ksport.common.core.service.IRedisService;
import com.killer.ksport.common.security.exception.PasswordIncorrectException;
import com.killer.ksport.common.security.exception.UserForbiddenException;
import com.killer.ksport.common.security.exception.UserInvalidException;
import com.killer.ksport.common.security.model.view.LoginUser;
import com.killer.ksport.common.core.controller.BaseController;
import com.killer.ksport.common.security.vo.RequestLoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.killer.ksport.common.core.web.ResponseBuilder;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author ：Killer
 * @date ：Created in 19-6-28 上午9:14
 * @description：${description}
 * @modified By：
 * @version: version
 */
@RestController
@RequestMapping("/")
public class LoginController extends BaseController {

    @Autowired
    private ILoginService loginService;
    @Autowired
    private IRedisService redisService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(@Valid RequestLoginUser requestLoginUser, BindingResult bindingResult) {
        // 检查有没有输入用户名密码和格式对不对
        Map<String, String> fieldErrorMap = getErrors(bindingResult);
        if (fieldErrorMap.size() > 0) {
            return new ResponseBuilder().error().message(getErrorsString(bindingResult)).add("fields", fieldErrorMap).build();
        }

        LoginUser loginUser = loginService.getLoginUser(requestLoginUser.getAccount(), requestLoginUser.getPassword());
        if (loginUser == null) {
            throw new UserInvalidException();
        }
        if (!loginUser.isEnable()) {
            throw new UserForbiddenException();
        }
        String token = loginService.generateToken(loginUser);
        //将用户信息缓存
        redisService.hSet(RedisKeyConstant.USER_DETAILS, loginUser.getUserId()+"", JSONObject.toJSONString(loginUser));
        return new ResponseBuilder().success().data(loginUser).add("token", token).build();
    }

}
