package com.cenhai.system.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * @TableName sys_user_auth
 */
@TableName(value ="sys_user_auth")
@Data
public class SysUserAuth implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long authId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 身份类型
     */
    private String identityType;

    /**
     * 身份标识
     */
    private String identifier;

    /**
     * 授权凭证
     */
    private String credential;

    /**
     * 是否验证
     */
    private String verified;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}