package com.cenhai.framework.security;

import com.cenhai.common.utils.IpUtils;
import com.cenhai.plugs.redis.service.RedisCache;
import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class TokenService {

    // 令牌自定义标识
    @Value("${token.header}")
    private String header;

    // 令牌秘钥
    @Value("${token.secret}")
    private String secret;

    // 令牌有效期（默认30分钟）
    @Value("${token.expireTime}")
    private int expireTime;

    @Autowired
    private RedisCache redisCache;

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public DefaultUserDetails getUserDetails(HttpServletRequest request)
    {
        String token = getToken(request);
        if (token != null) {
            Claims claims = parseToken(token);
            String uuid = (String) claims.get("access_token");
            DefaultUserDetails userDetails = redisCache.getCacheObject(createRedisCacheKey(uuid));
            verifyUserAgent(userDetails,request);
            return userDetails;
        }
        return null;
    }

    /**
     * 验证user-agent 如果环境改变不允许登录
     * @param userDetails
     * @param request
     */
    private void verifyUserAgent(DefaultUserDetails userDetails, HttpServletRequest request){
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        String clientDetails = userAgent.getOperatingSystem().getName() + "-" + userAgent.getBrowser().getName();
        String ipaddr = IpUtils.getIpAddr(request);
        if (!userDetails.getClientDetails().equals(clientDetails) && userDetails.getIpaddr().equals(ipaddr)){
            throw new InternalAuthenticationServiceException("登录环境异常，请重新登录");
        }
    }

    /**
     * 创建令牌
     *
     * @param userDetails 用户信息
     * @return 令牌
     */
    public String createToken(DefaultUserDetails userDetails)
    {
        String accessToken = UUID.randomUUID().toString();
        userDetails.setAccessToken(accessToken);
        setUserAgent(userDetails);
        refreshToken(userDetails);
        Map<String, Object> claims = new HashMap<>();
        claims.put("access_token",accessToken);
        return createToken(claims);
    }

    /**
     * 或者user-agent 并设置
     * @param userDetails
     */
    private void setUserAgent(DefaultUserDetails userDetails){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        UserAgent userAgent = UserAgent.parseUserAgentString(attributes.getRequest().getHeader("User-Agent"));
        userDetails.setClientDetails(userAgent.getOperatingSystem().getName() + "-" + userAgent.getBrowser().getName());
        userDetails.setIpaddr(IpUtils.getIpAddr(attributes.getRequest()));
    }

    /**
     * 验证令牌有效期，如果还有5分钟过期，则自动刷新
     * @param userDetails
     */
    public void verifyToken(DefaultUserDetails userDetails){
        long exp = userDetails.getExpireTime();
        long now = System.currentTimeMillis();
        if (exp - now <= 5 * 1000){
            refreshToken(userDetails);
        }
    }

    /**
     * 删除token
     * @param request
     */
    public void logout(HttpServletRequest request){
        String token = getToken(request);
        if (token != null) {
            Claims claims = parseToken(token);
            String uuid = (String) claims.get("access_token");
            redisCache.deleteObject(createRedisCacheKey(uuid));
        }
    }

    /**
     * 刷新令牌有效期
     * @param userDetails
     */
    public void refreshToken(DefaultUserDetails userDetails){
        Long nowTime = System.currentTimeMillis();
        if (userDetails.getLoginTime() == null){
            userDetails.setLoginTime(nowTime);
        }
        userDetails.setExpireTime(nowTime + expireTime * 1000);
        redisCache.setCacheObject(createRedisCacheKey(userDetails.getAccessToken()),userDetails,expireTime,TimeUnit.MINUTES);
    }


    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims)
    {
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token)
    {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    private String getToken(HttpServletRequest request)
    {
        String token = request.getHeader(header);
        return token;
    }

    private String createRedisCacheKey(String uuid){
        return "Authorization:access_token:" + uuid;
    }
}
