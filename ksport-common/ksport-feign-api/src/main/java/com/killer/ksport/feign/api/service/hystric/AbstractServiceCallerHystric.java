package com.killer.ksport.feign.api.service.hystric;

import com.killer.ksport.common.core.web.ResponseBuilder;

/**
 * @author ：Killer
 * @date ：Created in 19-7-4 下午2:37
 * @description：${description}
 * @modified By：
 * @version: version
 */
public abstract class AbstractServiceCallerHystric {
    protected Object responseError()
    {
        throw new RuntimeException("服务异常, 请稍后再试");
    }
}
