package com.cenhai.framework.security;

import com.cenhai.common.constant.Constants;
import com.cenhai.common.enums.ResultCode;
import com.cenhai.common.exception.ApiException;
import com.cenhai.common.utils.StringUtils;
import com.cenhai.framework.annotation.Log;
import com.cenhai.support.redis.service.RedisCache;
import com.cenhai.system.service.SysConfigService;
import com.pig4cloud.captcha.ArithmeticCaptcha;
import com.pig4cloud.captcha.ChineseGifCaptcha;
import com.pig4cloud.captcha.GifCaptcha;
import com.pig4cloud.captcha.SpecCaptcha;
import com.pig4cloud.captcha.base.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 暴露的token获取端点
 */
@Component
@RestController
public class TokenEndpoint {

    @Resource
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private SysConfigService configService;


    @RequestMapping(value = {"/auth/token"},method = {RequestMethod.POST})
    @Log(operType = "登录日志", desc = "'系统登录入口'")
    public Map<String,String> authenticate(@RequestBody Map<String,String> principal){
        validateCaptcha(principal.get("code"), principal.get("uuid"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal.get("username"),principal.get("password"));
        return createSuccessResult(authenticationManager.authenticate(authentication),Constants.DEFAULT_SECURITY_IDENTITY_TYPE);
    }

    /**
     * 获取图形验证码
     * @return
     */
    @GetMapping("/captcha")
    public Map<String, Object> getCaptcha(){
        int width = 130, height = 48, len = 5;
        Captcha captcha = null;
        String captchaType = configService.getConfigValue("captcha-type").toString();
        if (captchaType.equals("gif")){
            captcha = new GifCaptcha(width,height,len);
        }else if (captchaType.equals("spec")){
            captcha = new SpecCaptcha(width,height,len);
        }else if (captchaType.equals("chinese")){
            captcha = new ChineseGifCaptcha(width, height);
        }else {
            captcha = new ArithmeticCaptcha(width, height);
        }
        String code = captcha.text().toLowerCase();
        String key = UUID.randomUUID().toString();
        redisCache.setCacheObject(Constants.CAPTCHA_REDIS_KEY + StringUtils.nvl(key,""),code,Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        Map<String, Object> map = new HashMap<>();
        map.put("uuid",key);
        map.put("imageBase64", captcha.toBase64());
        return map;
    }

    /**
     * 校检图形验证码
     * @param code
     * @param uuid
     */
    public void validateCaptcha(String code, String uuid){
        String key = Constants.CAPTCHA_REDIS_KEY + StringUtils.nvl(uuid,"");
        String captcha = redisCache.getCacheObject(key);
        if (captcha == null){
            throw new ApiException(ResultCode.ERROR,"验证码已过期");
        }
        if (!code.equalsIgnoreCase(captcha)){
            throw new ApiException(ResultCode.ERROR,"验证码错误");
        }
    }

    /**
     * 创建成功返回
     * @param authentication
     * @param identityType
     * @return
     */
    private Map<String,String> createSuccessResult(Authentication authentication,String identityType){
        SystemUserDetails userDetails = (SystemUserDetails) authentication.getPrincipal();
        String token = tokenService.createToken(userDetails);
        Map<String,String> result = new HashMap<>();
        result.put("access_token",token);
        result.put("identity_type",identityType);
        result.put("expire_time",userDetails.getExpireTime().toString());
        return result;
    }
}
