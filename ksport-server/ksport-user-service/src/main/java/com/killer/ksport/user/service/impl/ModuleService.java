package com.killer.ksport.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.killer.ksport.common.core.db.dao.ksport.ModuleDao;
import com.killer.ksport.common.core.db.dao.ksport.PermissionDao;
import com.killer.ksport.common.core.db.dao.ksport.RolePermissionDao;
import com.killer.ksport.common.core.db.dao.ksport.UserInfoDao;
import com.killer.ksport.common.core.db.view.ksport.*;
import com.killer.ksport.common.core.exception.CommonException;
import com.killer.ksport.common.core.service.impl.BaseService;
import com.killer.ksport.common.core.util.CloneUtils;
import com.killer.ksport.user.db.dao.PermissionDaoExt;
import com.killer.ksport.user.service.IModuleService;
import com.killer.ksport.user.service.IUserService;
import com.killer.ksport.user.vo.ModuleVo;
import com.killer.ksport.user.vo.PermissionVo;
import com.killer.ksport.user.vo.RolePermissionVo;
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
public class ModuleService extends BaseService<ModuleDao,Module> implements IModuleService {

    @Autowired
    private PermissionDaoExt permissionDaoExt;
    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Transactional
    @Override
    public void savePermission(PermissionVo permissionVo) {
        Permission permission = CloneUtils.clone(permissionVo, Permission.class);
        permissionDaoExt.insert(permission);
    }

    @Transactional
    @Override
    public void assignPermission(RolePermissionVo rolePermissionVo) {
        //1.先删除旧的分配
        rolePermissionDao.delete(new UpdateWrapper<RolePermission>().eq("role_id", rolePermissionVo.getRoleId()));
        //2.分配新的
        if (!CollectionUtils.isEmpty(rolePermissionVo.getPermissionIds())) {
            RolePermission rolePermission=null;
            for (Long permissionId : rolePermissionVo.getPermissionIds()) {
                rolePermission=new RolePermission();
                rolePermission.setPermissionId(permissionId);
                rolePermission.setRoleId(rolePermissionVo.getRoleId());
                rolePermissionDao.insert(rolePermission);
            }
        }
    }

    @Override
    public List<Permission> listPermissionByUser(Long userId) {
        return permissionDaoExt.listPermissionByUser(userId);
    }
}
