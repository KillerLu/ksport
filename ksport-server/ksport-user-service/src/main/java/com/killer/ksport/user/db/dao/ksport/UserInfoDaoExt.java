package com.killer.ksport.user.db.dao.ksport;

import com.killer.ksport.common.core.db.dao.ksport.UserInfoDao;
import com.killer.ksport.common.core.db.view.ksport.Role;
import com.killer.ksport.user.vo.UserAccountVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ：Killer
 * @date ：Created in 19-8-14 上午9:58
 * @description：${description}
 * @modified By：
 * @version: version
 */
public interface UserInfoDaoExt extends UserInfoDao{

    public List<UserAccountVo> listUser(@Param("offset") Integer offset,@Param("pageLength")Integer pageLength);
}
