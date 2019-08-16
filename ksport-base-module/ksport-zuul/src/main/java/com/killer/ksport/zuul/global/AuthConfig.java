package com.killer.ksport.zuul.global;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.killer.ksport.common.core.db.view.auth.AuthAssignment;
import com.killer.ksport.zuul.service.IAuthAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：Killer
 * @date ：Created in 19-8-8 上午11:17
 * @description：${description}
 * @modified By：
 * @version: version
 */
@Component
public class AuthConfig {

    @Autowired
    private IAuthAssignmentService authAssignmentService;

    private static final Map<String,List<String>> authAssignmentMap=new HashMap<String,List<String>>();

    @PostConstruct
    public void loadAuthAssignment(){
        reloadAuthAssignment();
    }

    public void reloadAuthAssignment(){
        if (!authAssignmentMap.isEmpty()) {
            authAssignmentMap.clear();
        }
        List<AuthAssignment> authAssignments=authAssignmentService.list();
        if (CollectionUtils.isNotEmpty(authAssignments)) {
            for (AuthAssignment authAssignment : authAssignments) {
                if (authAssignmentMap.containsKey(authAssignment.getResourcePath())) {
                    authAssignmentMap.get(authAssignment.getResourcePath()).add(authAssignment.getPermissionCode());
                }else{
                    authAssignmentMap.put(authAssignment.getResourcePath(), new ArrayList<String>());
                    authAssignmentMap.get(authAssignment.getResourcePath()).add(authAssignment.getPermissionCode());
                }
            }
        }
    }

    public List<String> getRequirePermissions(String path){
        return authAssignmentMap.get(path);
    }

}
