package com.cenhai.common.exception;

import com.cenhai.common.enums.ResultCode;
import com.cenhai.common.enums.StatusCode;

/**
 * 接口异常
 * @author cenjihai
 */
public class ApiException extends DefaultException{
    public ApiException(StatusCode statusCode, String msg) {
        super(statusCode, msg);
    }

    public ApiException(String msg){
        super(ResultCode.ERROR,msg);
    }
}
