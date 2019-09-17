package com.killer.ksport.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.killer.ksport.common.core.constant.TransactionActionConstant;
import com.killer.ksport.common.core.db.dao.ksport.UserAccountDao;
import com.killer.ksport.common.core.db.view.ksport.UserAccount;
import com.killer.ksport.common.core.db.view.ksport.UserInfo;
import com.killer.ksport.common.core.db.view.message.TransactionMessage;
import com.killer.ksport.common.core.enums.RequestTypeEnum;
import com.killer.ksport.common.core.exception.CommonException;
import com.killer.ksport.common.core.model.TransactionMsgBody;
import com.killer.ksport.common.core.service.impl.BaseService;
import com.killer.ksport.common.core.util.CloneUtil;
import com.killer.ksport.common.security.util.MD5Util;
import com.killer.ksport.feign.api.service.GroupServiceCaller;
import com.killer.ksport.message.api.TransactionMsgApi;
import com.killer.ksport.user.db.dao.ksport.UserInfoDaoExt;
import com.killer.ksport.user.service.IModuleService;
import com.killer.ksport.user.service.IRoleService;
import com.killer.ksport.user.service.IUserService;
import com.killer.ksport.user.vo.UserAccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author ：Killer
 * @date ：Created in 19-7-3 下午2:57
 * @description：${description}
 * @modified By：
 * @version: version
 */
@Service
public class UserService extends BaseService<UserInfoDaoExt,UserInfo> implements IUserService {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IModuleService moduleService;
    @Autowired
    private UserAccountDao userAccountDao;
    @Autowired
    private GroupServiceCaller groupServiceCaller;
    @Autowired
    private TransactionMsgApi transactionMsgApi;

    @Transactional
    @Override
    public void saveUserAccount(UserAccountVo userAccountVo){
        //1.查询该用户是否存在
        int count = userAccountDao.selectCount(new QueryWrapper<UserAccount>().eq("account", userAccountVo.getAccount()));
        if (count > 0) {
            throw new CommonException("该用户名已存在");
        }
        //2.先添加该用户
        UserInfo userInfo = CloneUtil.clone(userAccountVo, UserInfo.class);
        super.save(userInfo);
        //3.添加账户
        UserAccount userAccount = CloneUtil.clone(userAccountVo, UserAccount.class);
        //密码需要md5加密
        userAccount.setPassword(MD5Util.generateSaltMd5(userAccount.getPassword()));
        userAccount.setUserId(userInfo.getId());
        userAccountDao.insert(userAccount);
    }

    @Override
    public List<UserAccountVo> listUser(Integer page, Integer pageLength) {
        return baseMapper.listUser(page>0?(page-1)*pageLength:0, pageLength);
    }

    @Override
    public UserAccount getUserAccountByUserId(Long userId) {
        return userAccountDao.selectOne(new QueryWrapper<UserAccount>().eq("user_id", userId));
    }

    @Transactional
    @Override
    public void updateUser(UserAccountVo userAccountVo) {
        //1.查询该用户信息
        UserInfo oldUserInfo = findExistById(userAccountVo.getId());
        //2.更新用户信息
        CloneUtil.copyPropertiesIgnoreNull(userAccountVo, oldUserInfo);
        baseMapper.updateById(oldUserInfo);
        //3.查询用户账户信息
        UserAccount oldUserAccount = getUserAccountByUserId(oldUserInfo.getId());
        //4.更新用户账户信息
        //这里要现将id置为null,以防覆盖userAccount的id
        userAccountVo.setId(null);
        CloneUtil.copyPropertiesIgnoreNull(userAccountVo, oldUserAccount);
        userAccountDao.updateById(oldUserAccount);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        //删除user同时需要删除群组用户关系,这里涉及分布式事务
        //一.预发送消息
        String uuid= UUID.randomUUID().toString();
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("userId", id);
        TransactionMsgBody transactionMsgBody = new TransactionMsgBody(id, uuid, "/deleteGroupUserByUserId", RequestTypeEnum.POST.getValue(), params);
        transactionMsgApi.prepareTransactionMsg(uuid, JSON.toJSONString(transactionMsgBody), TransactionActionConstant.DELETE_GROUP_USER_BY_USERID,"ksport-user","ksport-group");
        //二.执行本地事务操作
        //1.删除用户信息
        baseMapper.deleteById(id);
        //2.删除用户账户
        userAccountDao.delete(new UpdateWrapper<UserAccount>().eq("user_id", id));
        //3.删除用户角色关联关系
        roleService.deleteUserRoleByUserId(id);
        //三.确认并发送消息
        transactionMsgApi.confirmMsgToSend(uuid);
    }

    @Override
    public UserAccount getUserAccountByAccount(String account) {
        return userAccountDao.selectOne(new QueryWrapper<UserAccount>().eq("account", account));
    }

}
