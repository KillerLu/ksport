package com.killer.ksport.group.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.killer.ksport.common.core.db.dao.ksport.GroupDao;
import com.killer.ksport.common.core.db.dao.ksport.GroupUserDao;
import com.killer.ksport.common.core.db.view.ksport.Group;
import com.killer.ksport.common.core.db.view.ksport.GroupUser;
import com.killer.ksport.common.core.db.view.ksport.UserInfo;
import com.killer.ksport.common.core.exception.CommonException;
import com.killer.ksport.common.core.service.impl.BaseService;
import com.killer.ksport.common.core.util.CloneUtil;
import com.killer.ksport.group.constant.GroupType;
import com.killer.ksport.group.db.dao.ksport.GroupUserDaoExt;
import com.killer.ksport.group.service.IGroupService;
import com.killer.ksport.group.util.GroupUtil;
import com.killer.ksport.group.vo.GroupUserVo;
import com.killer.ksport.group.vo.GroupVo;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：Killer
 * @date ：Created in 19-8-16 下午4:16
 * @description：${description}
 * @modified By：
 * @version: version
 */
@Service
public class GroupService extends BaseService<GroupDao, Group> implements IGroupService {

    @Autowired
    private GroupUserDaoExt groupUserDaoExt;

    @Override
    @Transactional
    public void saveGroup(GroupVo groupVo) {
        //构建群组对象
        Group group = buildGroup(groupVo);
        //保存群组
        baseMapper.insert(group);
    }

    @Override
    @Transactional
    public void modifyGroup(GroupVo groupVo) {
        if (groupVo.getId() == null) {
            throw new CommonException("请选择对应群组");
        }
        //如果修改的是群组类型,如果有下级群组,则不允许修改
        if (groupVo.getType() != null && groupVo.getType().intValue() > 0) {
            List<Group> childs = listGroupByParentId(groupVo.getId());
            if (CollectionUtils.isNotEmpty(childs)) {
                throw new CommonException("该群组有下级群组,不支持修改类型");
            }
        }
        //1.查询旧group
        Group oldGroup = findExistById(groupVo.getId());
        //2.给该对象没有改变过的值
        CloneUtil.copyPropertiesIgnoreNull(groupVo, oldGroup);
        //3.用新的groupVo对象构建新的group,并设置新的id
        Group newGroup = buildGroup(groupVo);
        newGroup.setId(oldGroup.getId());
        //4.修改group
        baseMapper.updateById(newGroup);
    }

    @Override
    @Transactional
    public void deleteGroup(Long id) {
        //1.查询该群组
        Group group = findExistById(id);
        //2.判断群组类型
        switch (group.getType()) {
            case GroupType.SCHOOL:
            case GroupType.GRADE:
                //如果是学校或是年级,则在删除该群组的同时,还要删除其下的群组
                baseMapper.deleteById(id);
                List<Group> groups=baseMapper.selectList(new QueryWrapper<Group>().eq("parent_id", id));
                if (CollectionUtils.isNotEmpty(groups)) {
                    for (Group g : groups) {
                        deleteGroup(g.getId());
                    }
                }
            case GroupType.CLASS:
                //如果是班级,则同时要删除群组关系
                baseMapper.deleteById(id);
                groupUserDaoExt.delete(new UpdateWrapper<GroupUser>().eq("group_id", id));
                break;
        }
    }

    @Override
    @Transactional
    public void addGroupUser(GroupUserVo groupUserVo) {
        //1.校验群组,和群组用户是否存在以及是否重复加入同一群组
        checkGroupAndGroupUser(groupUserVo.getGroupId(), groupUserVo.getUserId());
        //2.添加关系
        GroupUser groupUser = CloneUtil.clone(groupUserVo, GroupUser.class);
        if (groupUser.isHeadTeacher() == null) {
            groupUser.setHeadTeacher(false);
        }
        groupUserDaoExt.insert(groupUser);
    }

    private void checkGroupAndGroupUser(Long groupId,Long userId) {
        //1.验证该用户是否存在(远程调用User服务)
        //TODO
        //2.验证群组是否存在
        Group group=findExistById(groupId);
        //3.判断加入的群组是否是班级(用户不能直接挂到年级和学校下)
        if (GroupType.CLASS != group.getType().intValue()) {
            throw new CommonException("用户只能加入到班级下");
        }
        //4.判断该用户是否已加入该群组
        int count=groupUserDaoExt.selectCount(new QueryWrapper<GroupUser>().eq("user_id", userId).eq("group_id", groupId));
        if (count > 0) {
            throw new CommonException("该用户已加入该群组,不能再次加入");
        }
    }

    @Override
    public void modifyGroupUser(GroupUserVo groupUserVo) {
        if (groupUserVo.getId() == null) {
            throw new CommonException("请选组群组用户");
        }
        //1.查询该群组用户关系
        GroupUser oldGroupUser = groupUserDaoExt.selectById(groupUserVo.getId());
        //2.赋予更新后的值
        CloneUtil.copyPropertiesIgnoreNull(groupUserVo, oldGroupUser);
        //3.校验更新后的值是否符合条件
        checkGroupAndGroupUser(oldGroupUser.getGroupId(), oldGroupUser.getUserId());
        //4.更改群组用户关系
        groupUserDaoExt.updateById(oldGroupUser);
    }

    @Override
    public void removeGroupUser(Long id) {
        groupUserDaoExt.deleteById(id);
    }


    private Group buildGroup(GroupVo groupVo) {
        Group group = null;
        switch (groupVo.getType()) {
            case GroupType.SCHOOL:
                group = buildSchoolGroup(groupVo);
                break;
            case GroupType.GRADE:
                group = buildGradeGroup(groupVo);
                break;
            case GroupType.CLASS:
                group = buildClassGroup(groupVo);
                break;
            default:
                throw new CommonException("请选择正确的群组类型");
        }
        return group;
    }

    private Group buildSchoolGroup(GroupVo groupVo) {
        if (StringUtils.isBlank(groupVo.getName())) {
            throw new CommonException("请填写学校名称");
        }
        Group group = CloneUtil.clone(groupVo, Group.class);
        group.setParentId(0L);
        group.setNumber(0);
        return group;
    }

    private Group buildGradeGroup(GroupVo groupVo) {
        if (groupVo.getParentId() == null) {
            throw new CommonException("请选择该年级所属的学校");
        }
        //判断该学校是否已经存在该年级
        int count = baseMapper.selectCount(new QueryWrapper<Group>().eq("parent_id", groupVo.getParentId()).eq("enroll_year", groupVo.getEnrollYear()));
        if (count > 0) {
            throw new CommonException("该学校已存在该年级");
        }
        Group group = CloneUtil.clone(groupVo, Group.class);
        group.setName(GroupUtil.getGradeName(groupVo.getEnrollYear()));
        group.setNumber(0);
        return group;
    }

    private Group buildClassGroup(GroupVo groupVo) {
        if (groupVo.getParentId() == null) {
            throw new CommonException("请选择该班级所属的年级");
        }
        if (groupVo.getNumber() == null || groupVo.getNumber().intValue() <= 0) {
            throw new CommonException("请选择该班级的序号(大于0)");
        }
        //查询该序号是否重复(同一年级只能有一个)
        int count = baseMapper.selectCount(new QueryWrapper<Group>().eq("parent_id", groupVo.getParentId()).eq("number", groupVo.getNumber()));
        if (count > 0) {
            throw new CommonException("该年级已存在该班");
        }
        Group group = CloneUtil.clone(groupVo, Group.class);
        group.setName(GroupUtil.getClassName(groupVo.getEnrollYear(), groupVo.getNumber()));
        return group;
    }

    @Override
    public List<Group> listGroupByParentId(Long parentId) {
        return baseMapper.selectList(new QueryWrapper<Group>().eq("parent_id", parentId));
    }

    @Override
    public GroupVo getGroupAndChildGroups(Long id) {
        //查询该群组
        Group group = findExistById(id);
        GroupVo groupVo = CloneUtil.clone(group, GroupVo.class);
        groupVo.setGroups(listChildGroups(id));
        return groupVo;
    }

    /**
     * 查询某群组下所有的子孙群组
     */
    private List<GroupVo> listChildGroups(Long id) {
        //查询子群组
        List<Group> groups = listGroupByParentId(id);
        List<GroupVo> groupVos = CloneUtil.batchClone(groups, GroupVo.class);
        if (CollectionUtils.isNotEmpty(groupVos)) {
            for (GroupVo groupVo : groupVos) {
                //递归查询,直到查到最下级的群组
                groupVo.setGroups(listChildGroups(groupVo.getId()));
            }
        }
        return groupVos;
    }


    @Override
    public List<GroupUserVo> listGroupUserDetailByGroupId(Long groupId) {
        return groupUserDaoExt.listGroupUserByGroupId(groupId);
    }

    @Override
    public void deleteGroupUserByUserId(Long userId) {
        groupUserDaoExt.delete(new UpdateWrapper<GroupUser>().eq("user_id", userId));
    }

}
