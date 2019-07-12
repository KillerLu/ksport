package com.killer.ksport.common.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 权限资源映射的数据源 <br>
 * 这里重写并实现了基于数据库的权限数据源 <br>
 * 实现了 {@link FilterInvocationSecurityMetadataSource}接口 <br>
 * 框架的默认实现是 {@link DefaultFilterInvocationSecurityMetadataSource} <br>
 *
 * @author killer
 */
@Service
public class AccessSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private final static Logger logger = LoggerFactory.getLogger(AccessSecurityMetadataSource.class);

    // key 是url+method ， value 是对应url资源的角色列表
    private static Map<RequestMatcher, Collection<ConfigAttribute>> permissionMap;


    /**
     * 在Web服务器启动时，缓存系统中的所有权限映射。<br>
     * 被{@link PostConstruct}修饰的方法会在服务器加载Servlet的时候运行(构造器之后,init()之前) <br/>
     */
    @PostConstruct
    private void loadResourceDefine() {
        permissionMap = new LinkedHashMap<>();
        //以后从数据库读取
        //TODO
        AntPathRequestMatcher requestMatcher = new AntPathRequestMatcher("/role/listRole");

        // 需要鉴权的url资源，@needAuth标志
//        List<SysPermissionDO> permissionList = authPermissionMapper.queryRolePermission();
//        for (SysPermissionDO permission : permissionList) {
//            String url = permission.getUrl();
//            String method = permission.getMethod();
//            String[] roles = permission.getRoleList().split(",");
//            log.info("{} - {}", url, method);
//            AntPathRequestMatcher requestMatcher = new AntPathRequestMatcher(url, method);
//
//            Collection<ConfigAttribute> attributes = new ArrayList<>();
//            for (String role : roles) {
//                attributes.add(new SecurityConfig(role));
//            }
//            // 占位符，需要权限才能访问的资源 都需要添加一个占位符，保证value不是空的
//            attributes.add(new SecurityConfig("@needAuth"));
//            permissionMap.put(requestMatcher, attributes);
//        }
//
//        // 公共的url资源 & 系统接口的url资源，value为null
//        List<SysPermissionDO> publicList = authPermissionMapper.queryPublicPermission();
//        for (SysPermissionDO permission : publicList) {
//            String url = permission.getUrl();
//            String method = permission.getMethod();
//            AntPathRequestMatcher requestMatcher = new AntPathRequestMatcher(url, "*".equals(method) ? null : method);
//            // value为空时不做鉴权，相当于所有人都可以访问该资源URL
//            permissionMap.put(requestMatcher, null);
//        }
//
//        // 多余的url资源， @noAuth，所有人都无法访问
        Collection<ConfigAttribute> attributes = new ArrayList<>();
        attributes.add(new SecurityConfig("BASKETBALL_V"));
        permissionMap.put(requestMatcher, attributes);

    }

    /**
     * 鉴权时会被AbstractSecurityInterceptor.beforeInvocation()调用，根据URL找到对应需要的权限
     * getAttributes方法返回本次访问需要的权限，可以有多个权限。
     * 在该实现中如果没有匹配的url直接返回null，也就是没有配置权限的url默认都为白名单，
     * 想要换成默认是黑名单只要修改getAllConfigAttributes的实现即可
     * @param object 安全对象类型 FilterInvocation.class
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //logger.info("[资源被访问：根据URL找到权限配置]: {}\n {}", object, permissionMap);

        if (permissionMap == null) {
            loadResourceDefine();
        }
        final HttpServletRequest request = ((FilterInvocation) object).getRequest();
        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : permissionMap.entrySet()) {
            if (entry.getKey().matches(request)) {
                //logger.info("[找到的Key]: {}", entry.getKey());
                //logger.info("[找到的Value]: {}", entry.getValue());
                if (entry.getValue().size() > 0) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 用于被AbstractSecurityInterceptor调用，返回所有的 Collection<ConfigAttribute> ，以筛选出不符合要求的attribute
     * getAllConfigAttributes方法如果返回了所有定义的权限资源，
     * Spring Security会在启动时校验每个ConfigAttribute是否配置正确，不需要校验直接返回null
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return new ArrayList<>();
    }

    /**
     * 用于被AbstractSecurityInterceptor调用，验证指定的安全对象类型是否被MetadataSource支持
     * supports方法返回类对象是否支持校验，web项目一般使用FilterInvocation来判断，或者直接返回true
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    public static void main(String[] args) {

    }
}
