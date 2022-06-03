package com.cenhai.common.constant;


public enum ResultStatus{
    OK(200,"成功"),
    ERROR(500,"错误"),
    UNAUTHORIZED(401,"未认证"),
    FORBIDDEN(403,"不允许访问");

    private Integer code;

    private String msg;

    ResultStatus(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
