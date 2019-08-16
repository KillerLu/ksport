//package com.killer.ksport.common.security.filter;
//
//import com.alibaba.fastjson.JSONObject;
//import com.killer.ksport.common.core.constant.RedisKeyConstant;
//import com.killer.ksport.common.core.service.IRedisService;
//import com.killer.ksport.common.core.service.impl.RedisService;
//import com.killer.ksport.common.core.util.SpringContextUtil;
//import com.killer.ksport.common.security.model.view.LoginUser;
//import com.killer.ksport.common.security.util.TokenUtils;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @author ：Killer
// * @date ：Created in 19-6-27 下午4:37
// * @description：${description}
// * @modified By：
// * @version: version
// */
//public class JWTAuthenticationFilter extends OncePerRequestFilter {
//
//    /**
//     * json web token 在请求头的名字
//     */
//    public static final String TOKEN_HEADER = "Authorization";
//
//
//    //1.从每个请求header获取token
//    //2.把用户信息(role等)以Authentication形式放进SecurityContext以备整个请求过程使用。
//    // （例如哪里需要判断用户权限是否足够时可以直接从SecurityContext取出去check）
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException
//    {
//        Authentication authentication= getAuthentication(request);
//
//        if (authentication == null)
//        {
//            chain.doFilter(request, response);
//            return;
//        }
//
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        chain.doFilter(request, response);
//
//    }
//
//    // 获取Authentication
//    private Authentication getAuthentication(HttpServletRequest request) {
//        // 从Header中拿到token
//        String token = request.getHeader(TOKEN_HEADER);
//        if (token != null) {
//            try {
//                Long userId = TokenUtils.getUserIdFromToken(token);
//                IRedisService redisService=(RedisService)SpringContextUtil.getBean("redisService");
//                //根据用户id获取对应的用户信息
//                LoginUser loginUser = JSONObject.parseObject(redisService.hGet(RedisKeyConstant.USER_DETAILS, userId + ""), LoginUser.class);
//                // 返回验证令牌
//                return loginUser != null ?
//                        new UsernamePasswordAuthenticationToken(loginUser, null, null) :
//                        null;
//            } catch (Exception e) {
//                logger.error("请求:" + request.getRequestURL() + "token解析出错");
//            }
//        }
//        return null;
//    }
//
//
//
//}
