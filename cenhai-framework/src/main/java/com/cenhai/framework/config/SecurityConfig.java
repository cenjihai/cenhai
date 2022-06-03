package com.cenhai.framework.config;

import com.alibaba.fastjson.JSON;
import com.cenhai.common.constant.ResultStatus;
import com.cenhai.common.web.domain.Result;
import com.cenhai.framework.security.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 跨域过滤器
     */
    @Autowired
    private CorsFilter corsFilter;

    @Resource
    private JwtAuthenticationTokenFilter authenticationTokenFilter;

    @Autowired
    private List<AuthenticationProvider> authenticationProviders;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(new UnauthorizedHandler()).and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/auth/token","/captcha").permitAll()
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/**/*.*").permitAll()
                .anyRequest().authenticated().and()
                .headers().frameOptions().disable();
        http.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
        http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 添加CORS filter
        http.addFilterBefore(corsFilter, JwtAuthenticationTokenFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        for (AuthenticationProvider authenticationProvider : authenticationProviders){
            auth.authenticationProvider(authenticationProvider);
        }
    }

    /**
     * 认证失败处理
     */
    class UnauthorizedHandler implements AuthenticationEntryPoint{
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
            if (authException instanceof InsufficientAuthenticationException) {
                result(response,new Result<>(ResultStatus.UNAUTHORIZED));
            }else {
                result(response,Result.error(authException.getMessage()));
            }
        }
    }

    /**
     * 自定义无权限访问返回
     */
    class CustomAccessDeniedHandler implements AccessDeniedHandler{

        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
            result(response,new Result<>(ResultStatus.FORBIDDEN));
        }
    }

    private void result(HttpServletResponse response, Result result) throws IOException {
        response.setHeader("content-type","application/json; charset=utf-8");
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().println(JSON.toJSONString(result));
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }
}
