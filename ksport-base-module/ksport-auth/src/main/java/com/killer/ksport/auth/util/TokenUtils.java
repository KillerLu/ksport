package com.killer.ksport.auth.util;

import com.killer.ksport.auth.model.TokenDetail;
import com.killer.ksport.auth.model.view.LoginUser;
import com.killer.ksport.common.core.db.view.ksport.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author ：Killer
 * @date ：Created in 19-6-27 下午5:03
 * @description：${description}
 * @modified By：
 * @version: version
 */
public class TokenUtils {


    /**
     * JWT加密密码
     */
    public static final String SECRET="leekawah123";

    /**
     * token过期时间
     */
    public static final Long EXPIRATION=604800L;

    /**
     * json web token 在请求头的名字
     */
    public static final String TOKEN_HEADER="Authorization";

    // JWT验证方法
    public static Authentication getAuthentication(HttpServletRequest request)
    {
        // 从Header中拿到token
        String token = request.getHeader(TOKEN_HEADER);
        if (token != null)
        {
            try
            {
                LoginUser loginUser = getLoginUserFromToken(token);
                List<GrantedAuthority> authorities = new ArrayList<>();
                if (loginUser != null) {
                    List<Role> roles=loginUser.getRoles();
                    if (!CollectionUtils.isEmpty(roles)) {
                        for (Role role : loginUser.getRoles())
                        {
                            authorities.add(new SimpleGrantedAuthority(role.getName()));
                        }
                    }
                }
                // 返回验证令牌
                return loginUser != null ?
                        new UsernamePasswordAuthenticationToken(loginUser, null, authorities) :
                        null;
            } catch (Exception e) {
                throw e;
            }
        }
        return null;
    }

    /**
     * 从 token 中拿到 username
     *
     * @param token
     * @return
     */
    public static LoginUser getLoginUserFromToken(String token) {
        try {
            final Claims claims = getClaimsFromToken(token);
            Long userId = Long.parseLong(claims.get("userId").toString());
            List<Role> roles=new ArrayList<Role>();
            String roleStr=claims.get("roles").toString();
            if (StringUtils.isNotBlank(roleStr)) {
                String[] roleStrArr=roleStr.split(",");
                for (String r : roleStrArr) {
                    roles.add(new Role(r));
                }
            }
            return new LoginUser(userId, roles);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析 token 的主体 Claims
     *
     * @param token
     * @return
     */
    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET.getBytes("UTF-8"))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 根据 TokenDetail 生成 Token
     *
     * @param tokenDetail
     * @return
     */
    public static String generateToken(TokenDetail tokenDetail) {
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("userId", tokenDetail.getUserId());
        claims.put("roles", generateRoleToken(tokenDetail.getRoles()));
        claims.put("created", generateCurrentDate());
        return generateToken(claims);
    }


    private static String generateRoleToken(List<Role> roles){
        if (CollectionUtils.isEmpty(roles)) {
            return "";
        }
        StringBuilder roleStr = new StringBuilder();
        for(int i = 0; i < roles.size(); i++) {
            if (roleStr.length() > 0) {//该步即不会第一位有逗号，也防止最后一位拼接逗号！
                roleStr.append(",");
            }
            roleStr.append(roles.get(i));
        }
        return roleStr.toString();
    }


    /**
     * 根据 claims 生成 Token
     *
     * @param claims
     * @return
     */
    private static String generateToken(Map<String, Object> claims) {
        try {
            return Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(generateExpirationDate())
                    .signWith(SignatureAlgorithm.HS512, SECRET.getBytes("UTF-8"))
                    .compact();
        } catch (UnsupportedEncodingException ex) {
            //didn't want to have this method throw the exception, would rather log it and sign the token like it was before
            //logger.warn(ex.getMessage());
            return Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(generateExpirationDate())
                    .signWith(SignatureAlgorithm.HS512, SECRET)
                    .compact();
        }
    }

    /**
     * token 过期时间
     *
     * @return
     */
    private static Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + EXPIRATION * 1000);
    }

    /**
     * 获得当前时间
     *
     * @return
     */
    private static Date generateCurrentDate() {
        return new Date(System.currentTimeMillis());
    }
}
