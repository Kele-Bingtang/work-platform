package cn.youngkbt.security.utils;

import cn.hutool.core.codec.Base64Decoder;
import cn.youngkbt.helper.SpringHelper;
import cn.youngkbt.jwt.properties.JwtProperties;
import cn.youngkbt.security.JwtAuthenticationToken;
import cn.youngkbt.utils.IdsUtil;
import cn.youngkbt.utils.ServletUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.*;

/**
 * @author Kele-Bingtang
 * @date 2022/12/10 21:37
 * @note JWT 工具类
 */
@Component
public class JwtTokenUtils {

    private static  String AUTHORITIES;
    /**
     * 密钥，自定义，根据密钥生成 token，或还原 token
     */
    private static String SECRET_KEY;
    /**
     * 有效期 12 小时：12 * 60 * 60 * 1000
     */
    private static long EXPIRE_TIME;
    
    public JwtTokenUtils(@Value("${jwt-token.authorities:authorities}") String authorities, @Value("${jwt.secret:work-uac-platform-abcdefghijklmnopqrstuvwxyz}") String secretKey,
                         @Value("${jwt.expire-time:#{12 * 60 * 60 * 1000}}") Long expireTime) {
        JwtTokenUtils.AUTHORITIES = authorities;
        JwtTokenUtils.SECRET_KEY = secretKey;
        JwtTokenUtils.EXPIRE_TIME = expireTime;
    }

    /**
     * 生成令牌
     *
     * @param authentication 认证信息
     * @return 令牌
     */
    public static String generateToken(Authentication authentication) {
        return generateToken(authentication, EXPIRE_TIME, null);
    }

    /**
     * 生成令牌
     *
     * @param authentication 认证信息
     * @param expireTime     Token 有效期
     * @return 令牌
     */
    public static String generateToken(Authentication authentication, Long expireTime) {
        return generateToken(authentication, expireTime, null);
    }

    public static String generateToken(Authentication authentication, Long expireTime, String namespace) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put(AUTHORITIES, authentication.getAuthorities());
        if (StringUtils.hasText(namespace)) {
            claims.put("namespace", namespace);
        }
        return generateToken(claims, expireTime);
    }

    /**
     * 生成令牌
     */
    private static String generateToken(Map<String, Object> claims, Long expireTime) {
        Date expirationDate = new Date(System.currentTimeMillis() + expireTime);
        String username = SecurityUtils.getUsername();
        return Jwts.builder()
                .header()
                .add("type", "JWT")
                .add("alg", "HS256")
                .and()
                .claims(claims)
                .id(IdsUtil.simpleUUID())
                .expiration(expirationDate)
                .subject(username)
                .issuer("work-uac")
                .issuedAt(new Date())
                .signWith(generateKey(), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * 加密明文密钥
     */
    public static SecretKey generateKey() {
        byte[] decodeKey = Base64Decoder.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(decodeKey);
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public static String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 根据请求令牌获取登录认证信息
     *
     * @param request 客户端的请求
     * @return 用户名
     */
    public static Authentication getAuthenticationFromRequest(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        return getAuthenticationFromToken(token);
    }

    /**
     * 根据请求令牌获取登录认证信息
     *
     * @param token jwt 密钥
     * @return 用户名
     */
    public static Authentication getAuthenticationFromToken(String token) {
        if (null == token) {
            return null;
        }
        Authentication authentication = null;
        // 如果在 Security 上下文检测没有登录过：1、程序重启后 session 清空。2、Spring Security 配置禁用 session，因此无法从 SecurityUtils.getAuthentication() 获取信息
        if (null == SecurityUtils.getAuthentication()) {
            // 根据 token 获取曾经登录的数据证明（防止恶意模拟 token）
            Claims claims = getClaimsFromToken(token);
            if (claims == null) {
                return null;
            }
            String username = claims.getSubject();
            if (username == null) {
                return null;
            }
            // 如果 token 过期
            if (isTokenExpired(token)) {
                return null;
            }
            // 获取用户的权限列表
            List<GrantedAuthority> authors = (List<GrantedAuthority>) claims.get(AUTHORITIES);
            // List<GrantedAuthority> authorities = new ArrayList<>();
            // // 如果用户权限是集合，则存入新的集合里
            // if (authors instanceof List authorList) {
            //     for (Object object : authorList) {
            //         authorities.add(new SimpleGrantedAuthority((String) ((Map) object).get("authority")));
            //     }
            // }
            authentication = new JwtAuthenticationToken(username, new WebAuthenticationDetailsSource().buildDetails(ServletUtil.getRequest()), authors, token, null);
        } else {
            // 如果上下文有用户登录过，则检查是否是当前用户
            if (validateToken(token, SecurityUtils.getUsername())) {
                // 如果上下文中 Authentication 非空，且请求令牌合法，直接返回当前登录认证信息
                authentication = SecurityUtils.getAuthentication();
            }
        }
        return authentication;
    }

    /**
     * 解析 Token
     *
     * @param token 令牌
     * @return 数据声明
     */
    private static Jws<Claims> parseClaimFromToken(String token) {
        return Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(token);
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private static Claims getClaimsFromToken(String token) {
        return parseClaimFromToken(token).getPayload();
    }

    /**
     * 从令牌中获取数据头
     *
     * @param token 令牌
     * @return 数据声明
     */
    private static JwsHeader getHeaderFromToken(String token) {
        return parseClaimFromToken(token).getHeader();
    }

    /**
     * 验证令牌
     *
     * @param token    令牌
     * @param username 用户名
     * @return 令牌是否正确
     */
    public static boolean validateToken(String token, String username) {
        String userName = getUsernameFromToken(token);
        return (username.equals(userName) && !isTokenExpired(token));
    }

    /**
     * 刷新令牌
     *
     * @param token 令牌
     * @return 新的令牌
     */
    public static String refreshToken(String token) {
        JwtProperties jwtProperties = SpringHelper.getBean(JwtProperties.class);
        return refreshToken(token, jwtProperties.getExpireTime());
    }

    public static String refreshToken(String token, Long expireTime) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            refreshedToken = generateToken(claims, expireTime);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public static boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 根据请求获取 token
     *
     * @param request 用户请求
     * @return 令牌
     */
    public static String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String tokenHead = "Bearer ";
        if (token == null) {
            token = request.getHeader("token");
        } else if (token.contains(tokenHead)) {
            // 把 Bearer 去掉，只要后面的 token 值
            token = token.substring(tokenHead.length());
        }
        if ("".equals(token)) {
            token = null;
        }
        return token;
    }

    public static Long getTokenTimeout() {
        JwtProperties jwtProperties = SpringHelper.getBean(JwtProperties.class);
        return jwtProperties.getExpireTime();
    }
    
}
