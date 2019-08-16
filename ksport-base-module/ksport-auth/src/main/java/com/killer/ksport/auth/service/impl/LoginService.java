package com.killer.ksport.auth.service.impl;


import com.killer.ksport.auth.service.ILoginService;
import com.killer.ksport.common.core.controller.ResponseBuilder;
import com.killer.ksport.common.core.exception.CommonException;
import com.killer.ksport.common.core.util.ClazzUtil;
import com.killer.ksport.feign.api.service.UserServiceCaller;
import com.killer.ksport.token.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author ：Killer
 * @date ：Created in 19-6-28 上午9:41
 * @description：${description}
 * @modified By：
 * @version: version
 */
@Service
public class LoginService implements ILoginService {

    @Autowired
    private UserServiceCaller userServiceCaller;

    @Override
    public LoginUser getLoginUser(String account, String password) {
        Map retMap=(Map)userServiceCaller.getLoginUser(account, password);
        if (ResponseBuilder.ERROR == Integer.parseInt(retMap.get("code").toString())) {
            throw new CommonException("用户名或密码错误");
        }
        return ClazzUtil.mapToBean((Map)retMap.get("data"), LoginUser.class);
    }

}
