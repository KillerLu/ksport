package com.killer.ksport.token.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

/**
 * @author ：Killer
 * @date ：Created in 19-8-9 上午11:01
 * @description：${description}
 * @modified By：
 * @version: version
 */
@Component
public class JwtTokenHelper {


    private static Logger logger = LoggerFactory.getLogger(JwtTokenHelper.class);
    /**
     * JWT加密密码
     */
    public final String SECRET = "leekawah123";
    /**
     *
     */
    @Value("${auth.token.expirationTime:1200000}")
    private long EXPIRATION_TIME;
    /**
     *
     */
    @Value("${auth.token.durationTime:3600000}")
    private long DURATION_TIME;


    /**
     * 根据userId生成token
     * @param userId
     * @return
     */
    public String generateToken(Long userId) {
        return Jwts.builder()
                .claim("userId", userId)
                .setExpiration(getTokenExpirationDate())
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    private Date getTokenExpirationDate(){
        return  new Date(System.currentTimeMillis() + EXPIRATION_TIME);
    }


    /**
     * 从token中获取userId
     * @param token
     * @return
     */
    public Long getUserIdFromToken(String token) {
        Long userId;
        try {
            Claims claims = getClaimsFromToken(token);
            userId = Long.parseLong(claims.get("userId").toString());
        } catch (Exception e) {
            userId=0l;
        }
        return userId;
    }

    /**
     * 解析 token 的主体 Claims
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }
}
