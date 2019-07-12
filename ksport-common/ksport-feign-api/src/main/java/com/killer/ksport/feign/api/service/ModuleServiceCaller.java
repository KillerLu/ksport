package com.killer.ksport.feign.api.service;

import com.killer.ksport.feign.api.service.hystric.ModuleServiceCallerHystric;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ：Killer
 * @date ：Created in 19-7-11 下午3:19
 * @description：${description}
 * @modified By：
 * @version: version
 */
@Service("moduleServiceCaller")
@FeignClient(value = "ksport-user", fallback = ModuleServiceCallerHystric.class)
public interface ModuleServiceCaller {

    @RequestMapping(value = "/module/listPermissionByUser", method = RequestMethod.GET)
    public Object listPermissionByUser(@RequestParam("userId") Long userId);
}
