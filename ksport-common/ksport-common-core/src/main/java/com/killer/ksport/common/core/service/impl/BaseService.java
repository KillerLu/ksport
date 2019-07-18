package com.killer.ksport.common.core.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.killer.ksport.common.core.controller.BaseController;
import com.killer.ksport.common.core.service.IBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;


/**
 * @author ：Killer
 * @date ：Created in 19-7-3 下午2:59
 * @description：${description}
 * @modified By：
 * @version: version
 */
public class BaseService<M extends BaseMapper<T>, T> extends ServiceImpl<M,T> implements IBaseService<T> {

    protected final static Logger logger = LoggerFactory.getLogger(BaseService.class);

    @Override
    public T findExistById(Serializable id) {
        T t = baseMapper.selectById(id);
        if (t == null) {
            throw new RuntimeException("对象不存在");
        }
        return t;
    }

}
