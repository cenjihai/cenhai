package com.cenhai.common.web.domain;


import com.cenhai.common.enums.ResultStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Result<T>{

    private Integer code;

    private String msg;

    private T data;

    private Long timestamp;

    public Result() {
        this.timestamp = System.currentTimeMillis();
    }

    public Result(ResultStatus resultStatus) {
        this.timestamp = System.currentTimeMillis();
        this.code = resultStatus.getCode();
        this.msg = resultStatus.getMsg();
    }

    public static <T> Result<T> success(T data){
        Result<T> result = new Result<>();
        result.setCode(ResultStatus.OK.getCode());
        result.setMsg(ResultStatus.OK.getMsg());
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success(){
        Result<T> result = new Result<>();
        result.setCode(ResultStatus.OK.getCode());
        result.setMsg(ResultStatus.OK.getMsg());
        return result;
    }

    public static <T> Result<T> success(String msg, T data){
        Result<T> result = new Result<>();
        result.setCode(ResultStatus.OK.getCode());
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static <T>Result<T> error(Integer code, String msg){
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static <T>Result<T> error(String msg){
        Result<T> result = new Result<>();
        result.setCode(ResultStatus.ERROR.getCode());
        result.setMsg(msg);
        return result;
    }

    public static <T>Result<T> result(boolean bool){
        if (bool){
            return new Result<>(ResultStatus.OK);
        }else {
            return new Result<>(ResultStatus.ERROR);
        }
    }
}
