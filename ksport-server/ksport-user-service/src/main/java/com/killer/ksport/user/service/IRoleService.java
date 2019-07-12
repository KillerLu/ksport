package com.killer.ksport.user.service;

import com.killer.ksport.common.core.db.view.ksport.Role;
import com.killer.ksport.common.core.db.view.ksport.UserInfo;
import com.killer.ksport.common.core.service.IBaseService;
import com.killer.ksport.user.vo.RoleVo;
import com.killer.ksport.user.vo.UserRoleVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：Killer
 * @date ：Created in 19-7-9 下午2:24
 * @description：${description}
 * @modified By：
 * @version: version
 */
public interface IRoleService extends IBaseService<Role> {

    /**
     * 分配用户角色
     */
    void assignRole(UserRoleVo userRoleVo);

    /**
     * 查询某用户所有角色
     */
    List<Role> listRoleByUser(Long userId);
}
