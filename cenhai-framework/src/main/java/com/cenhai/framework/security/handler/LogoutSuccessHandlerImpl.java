package com.cenhai.framework.security.handler;

import com.alibaba.fastjson.JSON;
import com.cenhai.common.utils.ServletUtils;
import com.cenhai.common.web.domain.Result;
import com.cenhai.framework.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LogoutSuccessHandlerImpl  implements LogoutSuccessHandler {

    @Autowired
    private TokenService tokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        tokenService.logout(request);
        ServletUtils.renderString(response,JSON.toJSONString( Result.success()));
    }
}
