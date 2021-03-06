package com.killer.ksport.common.core.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 当前使用的数据源
 *
 * @author mq
 */
public class MultipleDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSource();
    }
}
