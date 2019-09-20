package com.killer.ksport.feign.api.service;

import com.killer.ksport.feign.api.service.hystric.GroupServiceCallerHystric;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ：Killer
 * @date ：Created in 19-8-23 上午10:11
 * @description：${description}
 * @modified By：
 * @version: version
 */
@Service
@FeignClient(value = "ksport-group", fallback = GroupServiceCallerHystric.class)
public interface GroupServiceCaller {

    @RequestMapping(value = "/checkGroupUserByUserId", method = RequestMethod.GET)
    public Object checkGroupUserByUserId(@RequestParam("userId") long userId);
}
