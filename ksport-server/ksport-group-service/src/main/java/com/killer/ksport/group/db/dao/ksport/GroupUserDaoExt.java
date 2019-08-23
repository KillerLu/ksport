package com.killer.ksport.group.db.dao.ksport;

import com.killer.ksport.common.core.db.dao.ksport.GroupUserDao;
import com.killer.ksport.group.vo.GroupUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ：Killer
 * @date ：Created in 19-7-11 下午2:15
 * @description：${description}
 * @modified By：
 * @version: version
 */
public interface GroupUserDaoExt extends GroupUserDao {

    public List<GroupUserVo> listGroupUserByGroupId(@Param("groupId") Long groupId);
}
