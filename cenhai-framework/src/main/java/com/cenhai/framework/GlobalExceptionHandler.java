package com.cenhai.framework;

import com.cenhai.common.exception.ServiceException;
import com.cenhai.common.web.domain.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ServiceException.class)
    public Result serviceException(ServiceException e){
        e.printStackTrace();
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public Result badCredentialsException(BadCredentialsException e){
        e.printStackTrace();
        return Result.error("账号或密码错误");
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public Result usernameNotFoundException(UsernameNotFoundException e){
        e.printStackTrace();
        return Result.error("账号或密码错误");
    }

    @ExceptionHandler(LockedException.class)
    public Result lockedException(LockedException e){
        e.printStackTrace();
        return Result.error("用户已锁定");
    }

    @ExceptionHandler(CredentialsExpiredException.class)
    public Result credentialsExpiredException(CredentialsExpiredException e){
        e.printStackTrace();
        return Result.error("用户账号未验证");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidException(MethodArgumentNotValidException e){
        String msg = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        return Result.error(msg);
    }

    @ExceptionHandler(Exception.class)
    public Result exception(Exception e){
        e.printStackTrace();
        return Result.error("系统异常");
    }
}
