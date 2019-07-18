//package com.killer.ksport.common.core.config;
//
//import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
//import com.baomidou.mybatisplus.core.config.GlobalConfig;
//import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
//import com.baomidou.mybatisplus.extension.plugins.SqlExplainInterceptor;
//import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
//import com.google.common.collect.Maps;
//import com.killer.ksport.common.core.db.MultipleDataSource;
//import com.killer.ksport.common.core.enums.DataSourceEnum;
//import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//import java.util.Map;
//
///**
// * @author ：Killer
// * @date ：Created in 19-7-16 下午3:03
// * @description：${description}
// * @modified By：
// * @version: version
// */
//@Configuration
//@MapperScan("com.killer.ksport.**.dao")
//public class MybatisPlusConfig {
//
//    @Autowired
//    private GlobalConfig globalConfig;
//    @Autowired
//    private PaginationInterceptor paginationInterceptor;
//    @Autowired
//    private SqlExplainInterceptor sqlExplainInterceptor;
//
//
//
//    @Bean(name = "ksportDataSource")
//    @ConfigurationProperties(prefix = "ksport.datasource")
//    public DataSource ksportDataSource() {
//        //坑...,这里的属性名是user,而不是username
//        return new MysqlDataSource();
//        //return DruidDataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "quartzDataSource")
//    @ConfigurationProperties(prefix = "quartz.datasource")
//    public DataSource quartzDataSource() {
//        //坑...,这里的属性名是user,而不是username
//        return new MysqlDataSource();
//        //return DruidDataSourceBuilder.create().build();
//    }
//    /**
//     * 动态数据源配置
//     *
//     * @return 自定义数据源
//     */
//    @Bean
//    @Primary
//    public DataSource multipleDataSource(DataSource ksportDataSource,DataSource quartzDataSource) {
//        MultipleDataSource multipleDataSource = new MultipleDataSource();
//        Map<Object, Object> targetDataSources = Maps.newHashMap();
//        targetDataSources.put(DataSourceEnum.KSPORT.getValue(), ksportDataSource);
//        targetDataSources.put(DataSourceEnum.QUARTZ.getValue(), quartzDataSource);
//        //添加数据源
//        multipleDataSource.setTargetDataSources(targetDataSources);
//        //设置默认数据源
//        multipleDataSource.setDefaultTargetDataSource(ksportDataSource);
//        return multipleDataSource;
//    }
//
//    @Bean("sqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactory() throws Exception {
//        //坑.....,这里必须用MybatisSqlSessionFactoryBean 而不能用SqlSessionFactoryBean,否则访问baseMapper
//        //方法时会报not binding错误
//        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
//        //设置数据源
//        //这里是重点......********
//        //这里必须保证dataSource的创建顺序先于multipleDataSource,要不然会出现循环依赖问题
//        bean.setDataSource(multipleDataSource(ksportDataSource(),quartzDataSource()));
//        //设置mapper扫描路径
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:com/killer/ksport/**/*.xml"));
//        //设置全局配置
//        bean.setGlobalConfig(globalConfig);
//        bean.setPlugins(new Interceptor[]{
//                //添加分页功能
//                paginationInterceptor,
//                //添加分析插件
//                sqlExplainInterceptor
//        });
//        return bean.getObject();
//    }
//
//}
