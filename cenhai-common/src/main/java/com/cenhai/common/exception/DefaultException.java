package com.cenhai.common.exception;

public class DefaultException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer code;

    private String defaultMessage;

    public DefaultException(Integer code, String defaultMessage){
        super(defaultMessage);
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    public DefaultException(String defaultMessage){
        super(defaultMessage);
    }
}
