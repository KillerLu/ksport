package com.killer.ksport.common.core.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;

/**
 * @author ：Killer
 * @date ：Created in 19-7-3 下午2:58
 * @description：${description}
 * @modified By：
 * @version: version
 */
public interface IBaseService<T> extends IService<T> {
    T findExistById(Serializable id);
}
