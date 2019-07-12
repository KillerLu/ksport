package com.killer.ksport.common.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import java.io.IOException;

/**
 * 资源访问过滤器 <br>
 * 重写了{@link AbstractSecurityInterceptor} 接口 <br>
 * 默认的过滤器实现是{@link FilterSecurityInterceptor}
 *
 * @author killer
 */
@Component
public class AccessSecurityInterceptor extends FilterSecurityInterceptor implements Filter {


    private final static Logger logger = LoggerFactory.getLogger(AccessSecurityInterceptor.class);


    // 注入自定义鉴权决策管理器
    @Autowired
    private AccessDecisionManager ksportAccessDecisionManager;
    // 注入权限资源 SecurityMetadataSource
    @Autowired
    private AccessSecurityMetadataSource accessSecurityMetadataSource;



    /**
     * 初始化时将自定义的DecisionManager，注入到父类AbstractSecurityInterceptor中
     * 被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器执行一次。PostConstruct在构造函数之后执行，init（）方法之前执行
     */
    @PostConstruct
    public void initSetManager() {
        super.setAccessDecisionManager(ksportAccessDecisionManager);
    }

    /**
     * 重写父类AbstractSecurityInterceptor，获取到自定义MetadataSource的方法
     */
    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.accessSecurityMetadataSource;
    }

    /**
     * 初始化时将自定义的DecisionManager，注入到父类AbstractSecurityInterceptor中
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //logger.info("[自定义过滤器]: {}", " LoginSecurityInterceptor.doFilter()");
        FilterInvocation fi = new FilterInvocation(servletRequest, servletResponse, filterChain);
        // 调用父类的 beforeInvocation ==> accessDecisionManager.decide(..)
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            // 调用父类的 afterInvocation ==> afterInvocationManager.decide(..)
            super.afterInvocation(token, null);
        }
    }

    /**
     * 向父类提供要处理的安全对象类型，因为父类被调用的方法参数类型大多是Object，框架需要保证传递进去的安全对象类型相同
     */
    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }



    @Override
    public void destroy() {
    }

}
