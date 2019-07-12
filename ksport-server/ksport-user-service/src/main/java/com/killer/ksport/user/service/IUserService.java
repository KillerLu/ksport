package com.killer.ksport.user.service;

import com.killer.ksport.common.core.db.view.ksport.UserInfo;
import com.killer.ksport.common.core.service.IBaseService;
import com.killer.ksport.common.security.model.view.LoginUser;
import com.killer.ksport.user.vo.UserAccountVo;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：Killer
 * @date ：Created in 19-7-3 下午2:57
 * @description：${description}
 * @modified By：
 * @version: version
 */
public interface IUserService extends IBaseService<UserInfo> {

    /**
     * 新增用户账户
     * @param userAccountVo
     */
    void saveUserAccount(UserAccountVo userAccountVo);

    /**
     * 获取登录用户信息
     * @param account
     * @param password
     * @return
     */
    LoginUser getLoginUser(String account, String password);
}
