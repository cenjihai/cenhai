package com.cenhai.framework;

import com.cenhai.common.exception.ApiException;
import com.cenhai.common.web.domain.Result;
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
public class ControllerExceptionAdvice {

    /**
     * 接口异常处理，返回500
     * @param e
     * @return
     */
    @ExceptionHandler(ApiException.class)
    public Result apiException(ApiException e){
        e.printStackTrace();
        return new Result(e.getCode(),e.getMsg(),e.getMessage());
    }

    /**
     * 验证失败异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(BadCredentialsException.class)
    public Result badCredentialsException(BadCredentialsException e){
        e.printStackTrace();
        return Result.error("账号或密码错误");
    }

    /**
     * 用户名未找到异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public Result usernameNotFoundException(UsernameNotFoundException e){
        e.printStackTrace();
        return Result.error("账号或密码错误");
    }

    /**
     * 用户锁定异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(LockedException.class)
    public Result lockedException(LockedException e){
        e.printStackTrace();
        return Result.error("用户已锁定");
    }

    /**
     * 用户未验证异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(CredentialsExpiredException.class)
    public Result credentialsExpiredException(CredentialsExpiredException e){
        e.printStackTrace();
        return Result.error("用户账号未验证");
    }

    /**
     * 参数校检失败异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidException(MethodArgumentNotValidException e){
        String msg = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        return Result.error(msg);
    }

    /**
     * 默认异常 返回500
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result exception(Exception e){
        e.printStackTrace();
        return Result.error(e.getMessage());
    }
}
