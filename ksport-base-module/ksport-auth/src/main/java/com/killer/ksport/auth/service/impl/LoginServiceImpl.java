package com.killer.ksport.auth.service.impl;

import com.killer.ksport.auth.util.TokenUtils;
import com.killer.ksport.common.core.db.view.ksport.Role;
import com.killer.ksport.auth.model.TokenDetail;
import com.killer.ksport.auth.model.view.LoginUser;
import com.killer.ksport.auth.service.LoginService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：Killer
 * @date ：Created in 19-6-28 上午9:41
 * @description：${description}
 * @modified By：
 * @version: version
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Override
    public LoginUser getLoginUser(String account, String password) {
        //TODO   查询数据库
        List<Role> roles=new ArrayList<Role>();
        roles.add(new Role("Admin"));
        return new LoginUser(1l, "killer", "leekawah123", roles, true);
    }

    @Override
    public String generateToken(TokenDetail tokenDetail) {
        return TokenUtils.generateToken(tokenDetail);
    }
}
