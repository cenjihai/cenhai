package com.cenhai.common.exception;

import com.cenhai.common.enums.StatusCode;
import lombok.Getter;

/**
 * 系统默认异常
 * @author cenjihai
 */
@Getter
public class DefaultException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private int code;

    private String msg;

    public DefaultException(StatusCode statusCode, String msg){
        super(msg);
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
    }
}
