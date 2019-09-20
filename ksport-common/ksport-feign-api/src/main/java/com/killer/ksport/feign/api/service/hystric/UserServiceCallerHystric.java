package com.killer.ksport.feign.api.service.hystric;

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
public class UserServiceCallerHystric extends AbstractServiceCallerHystric implements UserServiceCaller {
    @Override
    public Object getLoginUser(String account, String password) {
        return responseError();
    }

    @Override
    public Object checkUser(Long id) {
        return responseError();
    }
}
