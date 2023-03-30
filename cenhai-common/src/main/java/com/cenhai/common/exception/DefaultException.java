package com.cenhai.common.exception;

import com.cenhai.common.enums.ResultCode;
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

    public DefaultException(StatusCode statusCode, String data){
        super(data);
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
    }

    public DefaultException(String msg){
        this.code = ResultCode.ERROR.getCode();
        this.msg = msg;
    }
}
