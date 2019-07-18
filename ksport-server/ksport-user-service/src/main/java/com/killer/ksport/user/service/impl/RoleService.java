package com.killer.ksport.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.killer.ksport.common.core.db.dao.ksport.UserRoleDao;
import com.killer.ksport.common.core.db.view.ksport.Role;
import com.killer.ksport.common.core.db.view.ksport.UserRole;
import com.killer.ksport.common.core.service.impl.BaseService;
import com.killer.ksport.user.db.dao.ksport.RoleDaoExt;
import com.killer.ksport.user.service.IRoleService;
import com.killer.ksport.user.vo.UserRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author ：Killer
 * @date ：Created in 19-7-9 下午2:26
 * @description：${description}
 * @modified By：
 * @version: version
 */
@Service
public class RoleService extends BaseService<RoleDaoExt,Role> implements IRoleService {

    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private RoleDaoExt roleDaoExt;

    @Transactional
    @Override
    public void assignRole(UserRoleVo userRoleVo) {
        //1.先删除旧的分配
        userRoleDao.delete(new UpdateWrapper<UserRole>().eq("user_id", userRoleVo.getUserId()));
        //2.分配新的
        if (!CollectionUtils.isEmpty(userRoleVo.getRoleIds())) {
            UserRole userRole=null;
            for (Long roleId : userRoleVo.getRoleIds()) {
                userRole=new UserRole();
                userRole.setRoleId(roleId);
                userRole.setUserId(userRoleVo.getUserId());
                userRoleDao.insert(userRole);
            }
        }
    }


    @Override
    public List<Role> listRoleByUser(Long userId) {
        return this.baseMapper.listRoleByUser(userId);
    }
}
