package com.killer.ksport.common.core.db.fill.filter.impl;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.killer.ksport.common.core.db.fill.filter.FieldFilter;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * @author ：Killer
 * @date ：Created in 19-7-2 下午2:43
 * @description：${description}
 * @modified By：
 * @version: version
 */
public class IsDeleteFieldFilter implements FieldFilter{

    /**
     * 属性名称
     */
    private static final String FIELD_NAME = "isDelete";

    @Override
    public void doFiller(MetaObjectHandler metaObjectHandler, MetaObject metaObject) {
        if (metaObject.hasSetter(FIELD_NAME)) {
            metaObjectHandler.setFieldValByName(FIELD_NAME, false, metaObject);
        }
    }
}
