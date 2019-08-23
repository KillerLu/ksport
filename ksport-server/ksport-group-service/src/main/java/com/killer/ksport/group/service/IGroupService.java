package com.killer.ksport.group.service;

import com.killer.ksport.common.core.db.view.ksport.Group;
import com.killer.ksport.common.core.service.IBaseService;
import com.killer.ksport.group.vo.GroupUserVo;
import com.killer.ksport.group.vo.GroupVo;

import java.util.List;

/**
 * @author ：Killer
 * @date ：Created in 19-8-16 下午4:15
 * @description：${description}
 * @modified By：
 * @version: version
 */
public interface IGroupService extends IBaseService<Group>{

    /**
     * 保存群组
     * @param groupVo
     */
    void saveGroup(GroupVo groupVo);

    /**
     * 修改群组
     * @param groupVo
     */
    void modifyGroup(GroupVo groupVo);

    /**
     * 删除群组
     * @param id
     */
    void deleteGroup(Long id);

    /**
     * 新增群组用户关系
     * @param groupUserVo
     */
    void addGroupUser(GroupUserVo groupUserVo);

    /**
     * 修改群组用户关系
     * @param groupUserVo
     */
    void modifyGroupUser(GroupUserVo groupUserVo);

    /**
     * 移除群组用户关系
     * @param id
     */
    void removeGroupUser(Long id);

    /**
     * 查询某群组下的所有下级群组
     * @param parentId
     * @return
     */
    List<Group> listGroupByParentId(Long parentId);

    /**
     * 查询某群组以及其下级群组
     * @param id
     * @return
     */
    GroupVo getGroupAndChildGroups(Long id);

    /**
     * 根据群组id查询群组用户详细信息(查询某群组下所有用户)
     * @param groupId
     * @return
     */
    List<GroupUserVo> listGroupUserDetailByGroupId(Long groupId);
}
