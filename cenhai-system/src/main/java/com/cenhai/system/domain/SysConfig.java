package com.cenhai.system.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 系统配置表
 * @TableName sys_config
 */
@TableName(value ="sys_config")
@Data
public class SysConfig implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long configId;

    /**
     * 配置名
     */
    private String configName;

    /**
     * 配置键
     */
    private String configKey;

    /**
     * 
     */
    private String configValue;

    /**
     * 控件 input select check radio
     */
    private String widget;

    /**
     * 选项
     */
    private String options;

    /**
     * 
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 备注说明
     */
    private String remark;

    /**
     * 状态
     */
    private String status;

    /**
     * 组id
     */
    private Long groupId;

    /**
     * 
     */
    private Integer configOrder;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}