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

    public static Result success(Object data){
        return new Result(ResultCode.OK, data);
    }

    public static Result error(String msg){
        return new Result(ResultCode.ERROR,msg);
    }

    public static Result error(StatusCode statusCode, String msg){
        return new Result(statusCode,msg);
    }

    public static Result resultBool(boolean bool, String msg){
        if (bool){
            return new Result(ResultCode.OK,msg);
        }else {
            return new Result(ResultCode.ERROR, msg);
        }
    }
}
