package com.killer.ksport.auth.filter;

import com.killer.ksport.auth.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：Killer
 * @date ：Created in 19-6-27 下午4:37
 * @description：${description}
 * @modified By：
 * @version: version
 */
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {


    /**
     * 辅助操作 token 的工具类
     */
    @Autowired
    private TokenUtils tokenUtils;



    public JWTAuthenticationFilter(AuthenticationManager authenticationManager)
    {
        super(authenticationManager);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        Authentication authentication= tokenUtils.getAuthentication(request);

        if (authentication == null)
        {
            chain.doFilter(request, response);
            return;
        }


        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);

    }

}
