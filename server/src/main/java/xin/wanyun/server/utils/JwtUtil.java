package xin.wanyun.server.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.util.Date;

/**
 * Jwt生成token类，校验token类
 */
public class JwtUtil {

    /**
     * jwt加密秘钥
     */
    private static final Key SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode("HaQ9vV85uqvqLHEMD0yZwd2TeeLyx869ek2xU36BufSirpjBV3N4PA1NkPdjv6FD"));

    /**
     * 过期秒
     */
    public static final Long ExpireTime = 604800L;

    /**
     * 创建jwt秘钥
     */
    public static String createToken(Long uid) {

        // 用户ID
        String sub = uid.toString();    // 将用户id转为string

        // 当前时间
        long currentTime = System.currentTimeMillis();

        // 生成JWT
        return Jwts.builder()
                .signWith(SecretKey, SignatureAlgorithm.HS256)
                .setHeaderParam("typ", "JWT")
                .setIssuer("xin.wanyun.server")
                .setSubject(sub)                         // 设置用户ID
                .setNotBefore(new Date(currentTime))     // 在此时间之前无效
                .setIssuedAt(new Date(currentTime))      // 设置签发时间
                .setExpiration(new Date(currentTime + ExpireTime * 1000))
                .compact();  // 设置过期时间
    }

    /**
     * 验证token是否有效
     */
    public static Long validateToken(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {

        // 这里会抛出异常，根据异常不同进行处理：
        // `ExpiredJwtException`：token过期
        // `MalformedJwtException`：错误的token（畸形）
        // `SignatureException`：签名异常
        // `IllegalArgumentException`：非法的token
        token = token.replaceAll("Bearer ", "");

        try {
            String sub = Jwts.parserBuilder()
                    .setSigningKey(SecretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            return Long.parseLong(sub);
        } catch (Exception e) {

        }
        return null;
    }

}
