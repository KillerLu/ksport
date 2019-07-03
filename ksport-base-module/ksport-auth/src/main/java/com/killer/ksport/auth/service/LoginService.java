package com.killer.ksport.auth.service;

import com.killer.ksport.auth.model.TokenDetail;
import com.killer.ksport.auth.model.view.LoginUser;

/**
 * @author ：Killer
 * @date ：Created in 19-6-28 上午9:39
 * @description：${description}
 * @modified By：
 * @version: version
 */
public interface LoginService {


    LoginUser getLoginUser(String account, String password);

    String generateToken(TokenDetail tokenDetail);
}
