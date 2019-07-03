package com.killer.ksport.common.core.db.fill;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.killer.ksport.common.core.db.fill.filter.FieldFilter;
import com.killer.ksport.common.core.db.fill.filter.impl.CreateTimeFieldFilter;
import com.killer.ksport.common.core.db.fill.filter.impl.IsDeleteFieldFilter;
import com.killer.ksport.common.core.db.fill.filter.impl.ModifyTimeFieldFilter;
import org.apache.ibatis.reflection.MetaObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：Killer
 * @date ：Created in 19-7-2 下午2:35
 * @description：公共字段填充处理器
 * @modified By：
 * @version: version
 */
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        List<FieldFilter> fillers = new ArrayList<FieldFilter>();
        fillers.add(new IsDeleteFieldFilter());
        fillers.add(new CreateTimeFieldFilter());
        fillers.add(new ModifyTimeFieldFilter());
        for (FieldFilter filler : fillers) {
            filler.doFiller(this, metaObject);
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        List<FieldFilter> fillers = new ArrayList<FieldFilter>();
        fillers.add(new ModifyTimeFieldFilter());
        for (FieldFilter filler : fillers) {
            filler.doFiller(this, metaObject);
        }
    }
}
