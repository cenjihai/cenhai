package com.cenhai.system.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 系统操作日志
 * @TableName sys_operlog
 */
@TableName(value ="sys_operlog")
@Data
public class SysOperlog implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long operId;

    /**
     * 操作用户编号
     */
    private Long userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 说明信息
     */
    private String info;

    /**
     * 操作者IP
     */
    private String operIp;

    /**
     * 接口url
     */
    private String operUrl;

    /**
     * 请求和响应json串
     */
    private String params;

    /**
     * 异常消息
     */
    private String errMsg;

    /**
     * 状态 0 正常，1异常
     */
    private String status;

    /**
     * 日志创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 登录用户信息
     */
    private String userDetails;

    /**
     * 处理的方法
     */
    private String action;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}