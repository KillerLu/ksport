package com.killer.ksport.auth.service;


import com.killer.ksport.token.model.LoginUser;

/**
 * @author ：Killer
 * @date ：Created in 19-6-28 上午9:39
 * @description：${description}
 * @modified By：
 * @version: version
 */
public interface ILoginService {


    LoginUser getLoginUser(String account, String password);

}
