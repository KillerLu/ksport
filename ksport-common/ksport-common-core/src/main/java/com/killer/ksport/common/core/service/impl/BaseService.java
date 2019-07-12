package com.killer.ksport.common.core.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.killer.ksport.common.core.service.IBaseService;


/**
 * @author ：Killer
 * @date ：Created in 19-7-3 下午2:59
 * @description：${description}
 * @modified By：
 * @version: version
 */
public class BaseService<M extends BaseMapper<T>, T> extends ServiceImpl<M,T> implements IBaseService<T> {

   

}
