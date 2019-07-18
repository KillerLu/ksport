package com.killer.ksport.auth.controller;

import com.killer.ksport.common.core.db.dao.ksport.RoleDao;
import com.killer.ksport.common.core.db.dao.quartz.JobEntityDao;
import com.killer.ksport.common.core.db.view.ksport.Role;
import com.killer.ksport.common.core.db.view.quartz.JobEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：Killer
 * @date ：Created in 19-7-17 上午11:03
 * @description：${description}
 * @modified By：
 * @version: version
 */
@RestController
@RequestMapping("/muti")
public class MultiDataSourceTestController {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private JobEntityDao jobEntityDao;



    @RequestMapping("/selectRoleById")
    public Role selectRoleById(){
        return roleDao.selectById(1l);
    }

    @RequestMapping("/selectJobById")
    public JobEntity selectJobById(){
        return jobEntityDao.selectById(1l);
    }

}
