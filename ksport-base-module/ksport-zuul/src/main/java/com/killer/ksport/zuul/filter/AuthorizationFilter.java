package com.killer.ksport.zuul.filter;

import com.alibaba.fastjson.JSONObject;
import com.killer.ksport.common.core.db.view.ksport.Permission;
import com.killer.ksport.redis.constant.RedisKeyConstant;
import com.killer.ksport.redis.service.IRedisService;
import com.killer.ksport.token.model.LoginUser;
import com.killer.ksport.token.util.JwtTokenHelper;
import com.killer.ksport.zuul.global.AuthConfig;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author ：Killer
 * @date ：Created in 19-8-7 下午4:26
 * @description：${description}
 * @modified By：
 * @version: version
 */
@Component
public class AuthorizationFilter extends ZuulFilter {

    protected final static Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);


    @Autowired
    private AuthConfig authConfig;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private IRedisService redisService;

    @Override
    public String filterType() {
        // 定义filter的类型，有pre、route、post、error四种
        return "pre";
    }

    @Override
    public int filterOrder() {
        // 定义filter的顺序，数字越小表示顺序越高，越先执行
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        // 表示是否需要执行该filter，true表示执行，false表示不执行
        return true;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        String uri = context.getRequest().getRequestURI();
        // 如果是访问登录接口, 则放行
        if (uri.startsWith("/auth/login")||uri.startsWith("/auth/verifyCode")) {
            return null;
        }
        try {
            checkToken(context);
        } catch (IOException e) {
            logger.error("", e);
        }
        return null;
    }

    private void checkToken(RequestContext context) throws IOException {
        logger.info("请求路径:{}", context.getRequest().getRequestURI());
        String token = context.getRequest().getHeader("Authorization");
        if (token == null) {
            logger.error("未传入token");
            responseErr(context, 401, "token is null or invalid");
            return;
        }
        context.addZuulRequestHeader("Authorization", token);
        String uri = context.getRequest().getRequestURI();
        List<String> codes = authConfig.getRequirePermissions(uri);
        if (CollectionUtils.isEmpty(codes)) {
            //没有配置代表不需要进行权限过滤
            return;
        }
        //解析该token
        Long userId = jwtTokenHelper.getUserIdFromToken(token);
        if (!checkPermission(userId, codes)) {
            logger.error("没有权限");
            responseErr(context, 403, "permission deny");
            return;
        }
        return;
    }

    private boolean checkPermission(Long userId,List<String> codes) {
        boolean flag=false;
        //根据用户id获取对应的用户信息
        try {
            LoginUser loginUser = JSONObject.parseObject(redisService.hGet(RedisKeyConstant.USER_DETAILS, userId + ""), LoginUser.class);
            if (!CollectionUtils.isEmpty(loginUser.getPermissions())) {
                for (String code : loginUser.getPermissions()) {
                    if (codes.contains(code)) {
                        flag=true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            //解析失败证明token错误,则无权限
            flag=false;
        }
        return flag;
    }

    private void responseErr(RequestContext context, int code, String message) throws IOException
    {
        context.setSendZuulResponse(false);
        context.setResponseStatusCode(code);
        context.getResponse().setHeader("Content-Type", "application/json;charset=UTF-8");
        context.getResponse().getWriter().print("{\"code\":" + code + ",\"message\":\"" + message + "\"}");
    }

}
