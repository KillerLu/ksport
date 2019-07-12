package com.killer.ksport.auth.service.impl;


import com.killer.ksport.auth.service.ILoginService;
import com.killer.ksport.common.security.util.TokenUtils;
import com.killer.ksport.common.core.util.ClazzUtil;
import com.killer.ksport.common.security.model.TokenDetail;
import com.killer.ksport.common.security.model.view.LoginUser;
import com.killer.ksport.feign.api.service.UserServiceCaller;
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
        return ClazzUtil.mapToBean((Map)userServiceCaller.getLoginUser(account, password), LoginUser.class);
    }

    @Override
    public String generateToken(TokenDetail tokenDetail) {
        return TokenUtils.generateToken(tokenDetail);
    }
}
