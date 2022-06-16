package com.cenhai.common.enums;

/**
 * 返回状态码枚举
 * @author cenjihai
 */
public enum ResultCode implements StatusCode{

    OK(200,"成功"),

    ERROR(500,"错误"),

    UNAUTHORIZED(401,"未认证"),
    FORBIDDEN(403,"禁止访问");

    private int code;

    private String msg;

    ResultCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
