package com.killer.ksport.common.core.config;


import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.SqlExplainInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author ：Killer
 * @date ：Created in 19-6-28 下午3:14
 * @description：${description}
 * @modified By：
 * @version: version
 */
@Configuration
@MapperScan(basePackages = {"com.killer.ksport.common.core.db.dao.auth"},sqlSessionTemplateRef = "authSqlSessionTemplate")
@EnableTransactionManagement(proxyTargetClass = true)
public class AuthMybatisPlusConfig {

    @Autowired
    private GlobalConfig globalConfig;
    @Autowired
    private PaginationInterceptor paginationInterceptor;
    @Autowired
    private SqlExplainInterceptor sqlExplainInterceptor;

    @Bean(name = "authDataSource")
    @ConfigurationProperties(prefix = "auth.datasource")
    public DataSource productDataSource() {
        return new MysqlDataSource();
        //return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "authSqlSessionFactory")
    public SqlSessionFactory ksportSqlSessionFactory(@Qualifier("authDataSource") DataSource dataSource) throws Exception {
        //坑.....,这里必须用MybatisSqlSessionFactoryBean 而不能用SqlSessionFactoryBean,否则访问baseMapper
        //方法时会报not binding错误
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        //设置数据源
        bean.setDataSource(dataSource);
        //设置mapper扫描路径
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:com/killer/ksport/**/auth/*.xml"));
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

    @Bean(name = {"authSqlSessionTemplate"})
    public SqlSessionTemplate quartzSqlSessionTemplate(@Qualifier("authSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
