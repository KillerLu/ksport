package com.killer.ksport.auth.conf;



import com.killer.ksport.common.security.filter.JWTAuthenticationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author ：Kille
 * @date ：Created in 19-6-26 上午9:47
 * @description：${description}
 * @modified By：
 * @version: version
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, securedEnabled = true)//开启@RolesAllowed 注解过滤权限
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login").permitAll()//登录接口不需要token
                .anyRequest().permitAll()       // 需携带有效 token
                .and()
                .csrf()
                .disable()                      // 禁用 Spring Security 自带的跨域处理
                .sessionManagement()                        // 定制我们自己的 session 策略
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 调整为让 Spring Security 不创建和使用 session

        /**
         * 本次 json web token 权限控制的核心配置部分
         * 在 Spring Security 开始判断本次会话是否有权限时的前一瞬间
         * 通过添加过滤器将 token 解析，将用户所有的权限写入本次 Spring Security 的会话
         */
        http
                .addFilterBefore((new JWTAuthenticationFilter()), UsernamePasswordAuthenticationFilter.class);
    }
}
