package com.killer.ksport.common.security.config;

import com.killer.ksport.common.core.db.view.ksport.Permission;
import com.killer.ksport.common.security.model.view.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;

/**
 * {@link AccessDecisionManager} 鉴权决策管理器 <br>
 * 被鉴权决策管理器 被{@link AbstractSecurityInterceptor} 调用进行鉴权 <br>
 * 框架默认实现是 {@link AffirmativeBased}
 *
 * @author killer
 */
@Service
public class KsportAccessDecisionManager implements AccessDecisionManager {

    private final static Logger logger = LoggerFactory.getLogger(KsportAccessDecisionManager.class);


    /**
     * 权限鉴定
     *
     * @param authentication   from SecurityContextHolder.getContext() =》 userDetails.getAuthorities()
     * @param object           是一个安全对象类型，FilterInvocation.class
     * @param configAttributes from MetaDataSource.getAttributes()，已经被框架做了非空判断
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        //logger.info("[资源权限]: {}", configAttributes);
        //logger.info("[用户权限]: {}", authentication.getAuthorities());

        Iterator<ConfigAttribute> it = configAttributes.iterator();
        while (it.hasNext()) {
            // 资源的权限
            ConfigAttribute resourceAttr = it.next();
            String resourceRole = ((SecurityConfig) resourceAttr).getAttribute();
            LoginUser loginUser=(LoginUser)authentication.getPrincipal();
            // 用户的权限
            for (Permission permission : loginUser.getPermissions()) {
                //logger.info("[资源角色==用户角色] ？ {} == {}", resourceRole.trim(), permission.getCode().trim());
                if (resourceRole.trim().equals(permission.getCode().trim())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足");
    }


    /**
     * 被AbstractSecurityInterceptor调用，遍历ConfigAttribute集合，筛选出不支持的attribute
     */
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    /**
     * 被AbstractSecurityInterceptor调用，验证AccessDecisionManager是否支持这个安全对象的类型。
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}
