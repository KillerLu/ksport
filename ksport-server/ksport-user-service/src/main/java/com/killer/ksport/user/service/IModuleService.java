package com.killer.ksport.user.service;

import com.killer.ksport.common.core.db.view.ksport.Module;
import com.killer.ksport.common.core.db.view.ksport.Permission;
import com.killer.ksport.common.core.db.view.ksport.UserInfo;
import com.killer.ksport.common.core.service.IBaseService;
import com.killer.ksport.user.vo.ModuleVo;
import com.killer.ksport.user.vo.PermissionVo;
import com.killer.ksport.user.vo.RolePermissionVo;

import java.util.List;

/**
 * @author ：Killer
 * @date ：Created in 19-7-9 下午2:24
 * @description：${description}
 * @modified By：
 * @version: version
 */
public interface IModuleService extends IBaseService<Module> {

    /**
     * 保存权限
     */
    void savePermission(PermissionVo permissionVo);

    /**
     * 分配权限
     */
    void assignPermission(RolePermissionVo rolePermissionVo);

    /**
     * 查询某用户所有权限
     */
    List<Permission> listPermissionByUser(Long userId);
}
