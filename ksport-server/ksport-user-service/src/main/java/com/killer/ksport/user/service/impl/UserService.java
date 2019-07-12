package com.killer.ksport.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.killer.ksport.common.core.db.dao.ksport.UserAccountDao;
import com.killer.ksport.common.core.db.dao.ksport.UserInfoDao;
import com.killer.ksport.common.core.db.view.ksport.Role;
import com.killer.ksport.common.core.db.view.ksport.UserAccount;
import com.killer.ksport.common.core.db.view.ksport.UserInfo;
import com.killer.ksport.common.core.enums.StatusEnum;
import com.killer.ksport.common.core.exception.CommonException;
import com.killer.ksport.common.core.service.impl.BaseService;
import com.killer.ksport.common.core.util.CloneUtils;
import com.killer.ksport.common.security.model.view.LoginUser;
import com.killer.ksport.user.service.IModuleService;
import com.killer.ksport.user.service.IRoleService;
import com.killer.ksport.user.service.IUserService;
import com.killer.ksport.user.vo.UserAccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：Killer
 * @date ：Created in 19-7-3 下午2:57
 * @description：${description}
 * @modified By：
 * @version: version
 */
@Service
public class UserService extends BaseService<UserInfoDao,UserInfo> implements IUserService {

    @Autowired
    private UserAccountDao userAccountDao;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IModuleService moduleService;

    @Transactional
    @Override
    public void saveUserAccount(UserAccountVo userAccountVo){
        //1.先添加该用户
        UserInfo userInfo = CloneUtils.clone(userAccountVo, UserInfo.class);
        super.save(userInfo);
        //2.添加账户
        UserAccount userAccount = CloneUtils.clone(userAccountVo, UserAccount.class);
        userAccount.setUserId(userInfo.getId());
        userAccountDao.insert(userAccount);
    }

    @Override
    public LoginUser getLoginUser(String account, String password) {
        //1.根据账户名查询该用户账户是否存在
        UserAccount userAccount = userAccountDao.selectOne(new QueryWrapper<UserAccount>().eq("account", account));
        if (userAccount == null||!userAccount.getPassword().equals(password)) {
            return null;
        }
        LoginUser loginUser = CloneUtils.clone(userAccount, LoginUser.class);
        //2.查询对应角色
        loginUser.setRoles(roleService.listRoleByUser(userAccount.getUserId()));
        //3.查询对应权限
        loginUser.setPermissions(moduleService.listPermissionByUser(userAccount.getUserId()));
        //4.设置是否禁用
        loginUser.setEnable(StatusEnum.NORMAL.getValue().equals(userAccount.getStatus()));
        return loginUser;
    }
}
