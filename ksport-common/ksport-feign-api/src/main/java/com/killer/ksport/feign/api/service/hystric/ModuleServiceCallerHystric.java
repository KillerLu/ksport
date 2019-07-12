package com.killer.ksport.feign.api.service.hystric;

import com.killer.ksport.common.core.db.view.ksport.Module;
import com.killer.ksport.feign.api.service.ModuleServiceCaller;
import com.killer.ksport.feign.api.service.UserServiceCaller;

/**
 * @author ：Killer
 * @date ：Created in 19-7-11 下午3:20
 * @description：${description}
 * @modified By：
 * @version: version
 */
public class ModuleServiceCallerHystric extends AbstractServiceCallerHystric implements ModuleServiceCaller {
    @Override
    public Object listPermissionByUser(Long userId) {
        return responseError();
    }
}
