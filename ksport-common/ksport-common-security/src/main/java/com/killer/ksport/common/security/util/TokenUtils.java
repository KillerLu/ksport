//package com.killer.ksport.common.security.util;
//
//import com.killer.ksport.common.core.util.SpringContextUtil;
//import com.killer.ksport.common.security.model.TokenDetail;
//import com.killer.ksport.common.security.model.view.LoginUser;
//import com.killer.ksport.common.core.db.view.ksport.Role;
//import com.killer.ksport.feign.api.service.ModuleServiceCaller;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.UnsupportedEncodingException;
//import java.util.*;
//
///**
// * @author ：Killer
// * @date ：Created in 19-6-27 下午5:03
// * @description：${description}
// * @modified By：
// * @version: version
// */
//public class TokenUtils {
//
//    /**
//     * JWT加密密码
//     */
//    public static final String SECRET = "leekawah123";
//
//    /**
//     * token过期时间
//     */
//    public static final Long EXPIRATION = 604800L;
//
//
//    /**
//     * 根据 TokenDetail 生成 Token
//     *
//     * @param tokenDetail
//     * @return
//     */
//    public static String generateToken(TokenDetail tokenDetail) {
//        if (tokenDetail == null) {
//            return null;
//        }
//        Map<String, Object> claims = new HashMap<String, Object>();
//        claims.put("userId", tokenDetail.getUserId());
//        claims.put("created", generateCurrentDate());
//        return generateToken(claims);
//    }
//
//    /**
//     * 根据 claims 生成 Token
//     *
//     * @param claims
//     * @return
//     */
//    private static String generateToken(Map<String, Object> claims) {
//        try {
//            return Jwts.builder()
//                    .setClaims(claims)
//                    .setExpiration(generateExpirationDate())
//                    .signWith(SignatureAlgorithm.HS512, SECRET.getBytes("UTF-8"))
//                    .compact();
//        } catch (UnsupportedEncodingException ex) {
//            //didn't want to have this method throw the exception, would rather log it and sign the token like it was before
//            //logger.warn(ex.getMessage());
//            return Jwts.builder()
//                    .setClaims(claims)
//                    .setExpiration(generateExpirationDate())
//                    .signWith(SignatureAlgorithm.HS512, SECRET)
//                    .compact();
//        }
//    }
//
//    /**
//     * 从令牌中获取userId
//     */
//    public static Long getUserIdFromToken(String token) {
//        Long userId;
//        try {
//            Claims claims = getClaimsFromToken(token);
//            userId = Long.parseLong(claims.get("userId").toString());
//        } catch (Exception e) {
//            userId=0l;
//        }
//        return userId;
//    }
//
//    /**
//     * 解析 token 的主体 Claims
//     *
//     * @param token
//     * @return
//     */
//    private static Claims getClaimsFromToken(String token) {
//        Claims claims;
//        try {
//            claims = Jwts.parser()
//                    .setSigningKey(SECRET.getBytes("UTF-8"))
//                    .parseClaimsJws(token)
//                    .getBody();
//        } catch (Exception e) {
//            claims = null;
//        }
//        return claims;
//    }
//    /**
//     * token 过期时间
//     *
//     * @return
//     */
//    private static Date generateExpirationDate() {
//        return new Date(System.currentTimeMillis() + EXPIRATION * 1000);
//    }
//
//    /**
//     * 获得当前时间
//     *
//     * @return
//     */
//    private static Date generateCurrentDate() {
//        return new Date(System.currentTimeMillis());
//    }
//}
