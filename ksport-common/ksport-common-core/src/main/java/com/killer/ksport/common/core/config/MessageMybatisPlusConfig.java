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
import org.springframework.context.annotation.Primary;
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
@MapperScan(basePackages = {"com.killer.ksport.common.core.db.dao.message"}, sqlSessionTemplateRef = "messageSqlSessionTemplate")
@EnableTransactionManagement(proxyTargetClass = true)
public class MessageMybatisPlusConfig {


    @Autowired
    private GlobalConfig globalConfig;
    @Autowired
    private PaginationInterceptor paginationInterceptor;
    @Autowired
    private SqlExplainInterceptor sqlExplainInterceptor;

    @Bean(name = "messageDataSource")
    @ConfigurationProperties(prefix = "message.datasource")
    public DataSource productDataSource() {
        //坑...,这里的属性名是user,而不是username
        return new MysqlDataSource();
    }

    @Bean(name = "messageSqlSessionFactory")
    public SqlSessionFactory ksportSqlSessionFactory(@Qualifier("messageDataSource")DataSource dataSource) throws Exception {
        //坑.....,这里必须用MybatisSqlSessionFactoryBean 而不能用SqlSessionFactoryBean,否则访问baseMapper
        //方法时会报not binding错误
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        //设置数据源
        bean.setDataSource(dataSource);
        //设置mapper扫描路径
        //这里注意:扫描的xml文件中每个方法会生成一个MapperStatemnt,放入Map<String, MappedStatement> mappedStatements中
        //每个文件只会扫描一次,多个数据源之间如果出现重复扫描,下一个数据源将扫描不到,当调用时就会报Invalid bound statement (not found)
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:com/killer/ksport/**/message/*.xml"));
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

    @Bean(name = {"messageSqlSessionTemplate"})
    public SqlSessionTemplate ksportSqlSessionTemplate(@Qualifier("messageSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
