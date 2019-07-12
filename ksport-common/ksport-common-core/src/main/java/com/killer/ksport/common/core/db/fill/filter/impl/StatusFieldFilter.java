package com.killer.ksport.common.core.db.fill.filter.impl;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.killer.ksport.common.core.db.fill.filter.FieldFilter;
import com.killer.ksport.common.core.enums.StatusEnum;
import org.apache.ibatis.reflection.MetaObject;

/**
 * @author ：Killer
 * @date ：Created in 19-7-4 上午9:55
 * @description：${description}
 * @modified By：
 * @version: version
 */
public class StatusFieldFilter implements FieldFilter {

    /**
     * 属性名称
     */
    private static final String FIELD_NAME = "status";

    @Override
    public void doFiller(MetaObjectHandler metaObjectHandler, MetaObject metaObject) {
        if (metaObject.hasSetter(FIELD_NAME)) {
            metaObjectHandler.setFieldValByName(FIELD_NAME, StatusEnum.NORMAL.getValue(), metaObject);
        }
    }
}
