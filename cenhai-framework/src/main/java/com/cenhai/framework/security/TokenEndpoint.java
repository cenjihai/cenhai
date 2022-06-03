package com.cenhai.framework.security;

import com.cenhai.common.constant.Constants;
import com.cenhai.common.constant.IdentityType;
import com.cenhai.common.exception.ServiceException;
import com.cenhai.common.utils.Base64;
import com.cenhai.common.utils.StringUtils;
import com.cenhai.common.web.domain.Result;
import com.cenhai.framework.annotation.Log;
import com.cenhai.framework.security.extension.GenericAuthenticationToken;
import com.cenhai.plugs.redis.service.RedisCache;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@RestController
public class TokenEndpoint {

    @Resource
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisCache redisCache;


    @RequestMapping(value = {"/auth/token"},method = {RequestMethod.POST})
    @Log(title = "登录日志", info = "系统登录入口")
    public Object authenticate(@RequestBody Map<String,String> principal){
        validateCaptcha(principal.get("code"), principal.get("uuid"));
        IdentityType identityType = IdentityType.resolve(principal.get("identity_type"));
        Authentication authentication = new GenericAuthenticationToken(principal.get("username"),principal.get("password"),identityType);
        return Result.success(createSuccessResult(authenticationManager.authenticate(authentication),identityType.getValue()));
    }

    @GetMapping("/captcha")
    public Result getCaptcha(){
        String capStr = null, code = null;
        String uuid = UUID.randomUUID().toString();
        String key = Constants.CAPTCHA_REDIS_KEY + uuid;
        String capText = captchaProducerMath.createText();
        capStr = capText.substring(0,capText.lastIndexOf("@"));
        code = capText.substring(capText.lastIndexOf("@") + 1);
        BufferedImage image = captchaProducerMath.createImage(capStr);
        redisCache.setCacheObject(key,code,Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image,"jpg",os);
        }catch (IOException e){
            return Result.error(e.getMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("uuid",uuid);
        map.put("imageBase64", Base64.encode(os.toByteArray()));
        return Result.success(map);
    }

    public void validateCaptcha(String code, String uuid){
        String key = Constants.CAPTCHA_REDIS_KEY + StringUtils.nvl(uuid,"");
        String captcha = redisCache.getCacheObject(key);
        if (captcha == null){
            throw new ServiceException("验证码错误");
        }
        if (!code.equalsIgnoreCase(captcha)){
            throw new ServiceException("验证码错误");
        }
    }

    private Map<String,String> createSuccessResult(Authentication authentication,String identityType){
        DefaultUserDetails userDetails = (DefaultUserDetails) authentication.getDetails();
        String token = tokenService.createToken(userDetails);
        Map<String,String> result = new HashMap<>();
        result.put("access_token",token);
        result.put("identity_type",identityType);
        result.put("expire_time",userDetails.getExpireTime().toString());
        return result;
    }
}