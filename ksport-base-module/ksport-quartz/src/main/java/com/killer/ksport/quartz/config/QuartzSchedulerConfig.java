package com.killer.ksport.quartz.config;

import com.killer.ksport.common.core.config.QuartzMybatisPlusConfig;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Properties;

/**
 * @author ：Killer
 * @date ：Created in 19-7-12 下午4:48
 * @description：${description}
 * @modified By：
 * @version: version
 */
@Configuration
public class QuartzSchedulerConfig {

    @Resource(name="quartzDataSource")
    private DataSource dataSource;

    //配置JobFactory
    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    //从quartz.properties文件中读取Quartz配置属性
    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }


    //配置JobFactory,为quartz作业添加自动连接支持
    //AutowiringSpringBeanJobFactory工厂类将负责生成实现了Job接口的类的实例对象Bean
    public final class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory implements
            ApplicationContextAware {
        private transient AutowireCapableBeanFactory beanFactory;
        @Override
        public void setApplicationContext(final ApplicationContext context) {
            beanFactory = context.getAutowireCapableBeanFactory();
        }
        @Override
        protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
            final Object job = super.createJobInstance(bundle);
            beanFactory.autowireBean(job);
            return job;
        }
    }


    //---------------------------------静态调度,定义JobDetail和触发器,通过SchedulerFactoryBean进行注册调度
    /**
     * SchedulerFactoryBean这个类的真正作用提供了对org.quartz.Scheduler的创建与配置，并且会管理它的生命周期与Spring同步。
     * org.quartz.Scheduler: 调度器。所有的调度都是由它控制。
     * @param dataSource 为SchedulerFactory配置数据源
     * @param jobFactory 为SchedulerFactory配置JobFactory
     */
//    @Bean
//    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory,DataSource dataSource,CronTrigger[] cronTrigger, JobDetail[]
//            jobDetails) throws IOException {
//        SchedulerFactoryBean factory = new SchedulerFactoryBean();
//        //可选,QuartzScheduler启动时更新己存在的Job,这样就不用每次修改targetObject后删除qrtz_job_details表对应记录
//        factory.setOverwriteExistingJobs(true);
//        factory.setAutoStartup(true); //设置自行启动
//        factory.setDataSource(dataSource);
//        factory.setJobFactory(jobFactory);
//        factory.setTriggers(cronTrigger);
//        factory.setJobDetails(jobDetails);
//        factory.setQuartzProperties(quartzProperties());
//        return factory;
//    }
//
//
//    @Bean(name = "jobDetail")
//    public JobDetailFactoryBean job1Detail() {
//        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
//        jobDetailFactoryBean.setJobClass(KsportQuartzJobBean.class);
//        jobDetailFactoryBean.setDurability(true);
//        return jobDetailFactoryBean;
//    }
//
//    @Bean(name = "jobTrigger")
//    public CronTriggerFactoryBean job1Trigger(@Qualifier("jobDetail") JobDetail jobDetail) {
//        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
//        cronTriggerFactoryBean.setJobDetail(jobDetail);
//        cronTriggerFactoryBean.setCronExpression("0/5 * * * * ?");
//        return cronTriggerFactoryBean;
//    }

    //---------------------------------动态调度
    /**
     * SchedulerFactoryBean这个类的真正作用提供了对org.quartz.Scheduler的创建与配置，并且会管理它的生命周期与Spring同步。
     * org.quartz.Scheduler: 调度器。所有的调度都是由它控制。
     * @param jobFactory 为SchedulerFactory配置JobFactory
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        //可选,QuartzScheduler启动时更新己存在的Job,这样就不用每次修改targetObject后删除qrtz_job_details表对应记录
        factory.setOverwriteExistingJobs(true);
        factory.setAutoStartup(true); //设置自行启动
        factory.setDataSource(dataSource);
        factory.setJobFactory(jobFactory);
        factory.setQuartzProperties(quartzProperties());
        return factory;
    }


}
