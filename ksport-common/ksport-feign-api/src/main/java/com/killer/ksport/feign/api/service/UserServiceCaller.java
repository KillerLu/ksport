package com.killer.ksport.feign.api.service;

import com.killer.ksport.feign.api.service.hystric.UserServiceCallerHystric;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ：Killer
 * @date ：Created in 19-7-4 下午2:33
 * @description：${description}
 * @modified By：
 * @version: version
 */
@Service
@FeignClient(value = "ksport-user", fallback = UserServiceCallerHystric.class)
public interface UserServiceCaller {

    @RequestMapping(value = "/user/getLoginUser", method = RequestMethod.GET)
    public Object getLoginUser(@RequestParam("account") String account,
                                 @RequestParam("password") String password);
}
