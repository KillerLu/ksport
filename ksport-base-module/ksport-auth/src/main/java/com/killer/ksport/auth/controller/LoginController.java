package com.killer.ksport.auth.controller;

import com.killer.ksport.auth.model.view.LoginUser;
import com.killer.ksport.common.security.vo.RequestLoginUser;
import com.killer.ksport.auth.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.killer.ksport.common.core.web.ResponseBuilder;

import javax.validation.Valid;

/**
 * @author ：Killer
 * @date ：Created in 19-6-28 上午9:14
 * @description：${description}
 * @modified By：
 * @version: version
 */
@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Object login(@Valid RequestLoginUser requestLoginUser, BindingResult bindingResult){
        // 检查有没有输入用户名密码和格式对不对
        if (bindingResult.hasErrors()){
            //TODO
        }

        LoginUser loginUser = loginService.getLoginUser(requestLoginUser.getAccount(),requestLoginUser.getPassword());
        if (loginUser == null) {
            //TODO
        }
        String token = loginService.generateToken(loginUser);
        return new ResponseBuilder().success().data(loginUser).add("token", token).build();
    }

//    private ResultMap checkAccount(RequestLoginUser requestLoginUser, LoginDetail loginDetail){
//        if (loginDetail == null){
//            return new ResultMap().fail("434").message("账号不存在！").data("");
//        }else {
//            if (loginDetail.enable() == false){
//                return new ResultMap().fail("452").message("账号在黑名单中").data("");
//            }
//            if (!loginDetail.getPassword().equals(requestLoginUser.getPassword())){
//                return new ResultMap().fail("438").message("密码错误！").data("");
//            }
//        }
//        return null;
//    }
}
