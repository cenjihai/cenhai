package com.cenhai.common.web.domain;


import com.cenhai.common.enums.ResultCode;
import com.cenhai.common.enums.StatusCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Result{

    private Integer code;

    private String msg;

    private Object data;

    private Long timestamp;

    public Result(int code, String msg, Object data){
        this.timestamp = System.currentTimeMillis();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(StatusCode statusCode, Object data){
        this.timestamp = System.currentTimeMillis();
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
        this.data = data;
    }

    /**
     * 通用返回成功
     * @param data 返回的数据
     * @return
     */
    public static Result success(Object data){
        return new Result(ResultCode.OK, data);
    }

    /**
     * 通用返回错误
     * @param msg 错误信息
     * @return
     */
    public static Result error(String msg){
        return new Result(ResultCode.ERROR.getCode(),msg,null);
    }

    /**
     * 返回错误
     * @param statusCode 错误枚举
     * @param data 错误时携带的数据
     * @return
     */
    public static Result error(StatusCode statusCode, String data){
        return new Result(statusCode,data);
    }
}
