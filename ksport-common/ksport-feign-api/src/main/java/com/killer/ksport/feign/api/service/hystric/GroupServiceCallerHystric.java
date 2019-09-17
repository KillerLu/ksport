package com.killer.ksport.feign.api.service.hystric;

import com.killer.ksport.feign.api.service.GroupServiceCaller;
import com.killer.ksport.feign.api.service.UserServiceCaller;
import org.springframework.stereotype.Component;

/**
 * @author ：Killer
 * @date ：Created in 19-7-4 下午2:36
 * @description：${description}
 * @modified By：
 * @version: version
 */
@Component
public class GroupServiceCallerHystric extends AbstractServiceCallerHystric implements GroupServiceCaller {

    @Override
    public Object testAdd() {
        return responseError();
    }
}
