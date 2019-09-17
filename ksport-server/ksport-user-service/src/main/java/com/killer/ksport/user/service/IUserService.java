package com.killer.ksport.user.service;

import com.killer.ksport.common.core.db.view.ksport.UserAccount;
import com.killer.ksport.common.core.db.view.ksport.UserInfo;
import com.killer.ksport.common.core.service.IBaseService;
import com.killer.ksport.user.vo.UserAccountVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
     * 查询用户信息列表
     * @param page
     * @param pageLength
     * @return
     */
    List<UserAccountVo> listUser(Integer page, Integer pageLength);

    /**
     * 根据用户id查询用户账号
     * @param userId
     * @return
     */
    UserAccount getUserAccountByUserId(Long userId);

    /**
     * 更新用户信息
     */
    void updateUser(UserAccountVo userAccountVo);

    /**
     * 删除用户
     * @param id
     */
    void deleteUser(Long id);

    /**
     * 根据用户名查询用户
     * @param account
     * @return
     */
    UserAccount getUserAccountByAccount(String account);


}
