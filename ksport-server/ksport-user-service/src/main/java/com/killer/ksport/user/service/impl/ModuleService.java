package com.killer.ksport.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.killer.ksport.common.core.db.dao.ksport.ModuleDao;
import com.killer.ksport.common.core.db.dao.ksport.RolePermissionDao;
import com.killer.ksport.common.core.db.view.ksport.*;
import com.killer.ksport.common.core.service.impl.BaseService;
import com.killer.ksport.common.core.util.CloneUtil;
import com.killer.ksport.user.db.dao.ksport.PermissionDaoExt;
import com.killer.ksport.user.service.IModuleService;
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
        Permission permission = CloneUtil.clone(permissionVo, Permission.class);
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

    @Override
    public void deleteRolePermissionByRoleId(Long roleId) {
        rolePermissionDao.delete(new UpdateWrapper<RolePermission>().eq("role_id", roleId));
    }

    @Transactional
    @Override
    public void deleteModule(Long id) {
        //1.删除该模块
        baseMapper.deleteById(id);
        //2.查询该模块下的所有权限
        List<Permission> permissions=permissionDaoExt.selectList(new QueryWrapper<Permission>().eq("module_id", id));
        //3.删除该模块下所有权限
        permissionDaoExt.delete(new UpdateWrapper<Permission>().eq("module_id", id));
        //4.遍历这些权限,删除这些权限所对应的角色-权限关联关系
        if (!CollectionUtils.isEmpty(permissions)) {
            for (Permission permission : permissions) {
                deleteRolePermissionByPermissionId(permission.getId());
            }
        }
    }

    @Override
    public void deleteRolePermissionByPermissionId(Long permissionId) {
        rolePermissionDao.delete(new UpdateWrapper<RolePermission>().eq("permission_id", permissionId));
    }

    @Override
    public IPage listPermissionByModule(Long id,Integer page,Integer pageLength) {
        return permissionDaoExt.selectPage(new Page<Permission>(page, pageLength), new QueryWrapper<Permission>().eq("module_id", id));
    }

    @Override
    public void modifyPermission(PermissionVo permissionVo) {
        //1.查询旧的权限
        Permission oldPermission=permissionDaoExt.selectById(permissionVo.getId());
        //2.更新数据
        CloneUtil.copyPropertiesIgnoreNull(permissionVo, oldPermission);
        permissionDaoExt.updateById(oldPermission);
    }

    @Transactional
    @Override
    public void deletePermission(Long id) {
        //1.删除该权限
        permissionDaoExt.deleteById(id);
        //2.删除该权限对应的角色-权限对应关系
        deleteRolePermissionByPermissionId(id);
    }


}
