package com.killer.ksport.common.core.config;


import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.SqlExplainInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：Killer
 * @date ：Created in 19-6-28 下午3:14
 * @description：${description}
 * @modified By：
 * @version: version
 */
@Configuration
@MapperScan(basePackages = {"com.killer.ksport.common.core.db.dao.ksport"})
@EnableTransactionManagement
public class KsportMybatisPlusConfig {


    @Autowired
    private GlobalConfig globalConfig;
    @Autowired
    private PaginationInterceptor paginationInterceptor;
    @Autowired
    private SqlExplainInterceptor sqlExplainInterceptor;

    @Bean(name = "ksportDataSource")
    @ConfigurationProperties(prefix = "ksport.datasource")
    @Primary
    public DataSource productDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "ksportSqlSessionFactory")
    @Primary
    public SqlSessionFactory ksportSqlSessionFactory(DataSource dataSource) throws Exception {
        //坑.....,这里必须用MybatisSqlSessionFactoryBean 而不能用SqlSessionFactoryBean,否则访问baseMapper
        //方法时会报not binding错误
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        //设置数据源
        bean.setDataSource(dataSource);
        //设置mapper扫描路径
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:com/killer/ksport/common/core/db/mapping/ksport/*.xml"));
        //设置全局配置
        bean.setGlobalConfig(globalConfig);
        bean.setPlugins(new Interceptor[]{
                //添加分页功能
                paginationInterceptor,
                //添加分析插件
                sqlExplainInterceptor
        });
        return bean.getObject();
    }


}
