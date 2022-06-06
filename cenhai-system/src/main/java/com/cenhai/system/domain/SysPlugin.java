package com.cenhai.system.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 插件表
 * @TableName sys_plugin
 */
@TableName(value ="sys_plugin")
@Data
public class SysPlugin implements Serializable {
    /**
     * 插件ID
     */
    @TableId
    private String pluginId;

    /**
     * 插件包绝对地址
     */
    private String pluginPath;

    /**
     * 插件名称
     */
    private String name;

    /**
     * 详细描述
     */
    private String description;

    /**
     * 插件版本
     */
    private String version;

    /**
     * 插件状态 0 启用 1 禁用
     */
    private String status;

    /**
     * 是否安装
     */
    private String isInstall;

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