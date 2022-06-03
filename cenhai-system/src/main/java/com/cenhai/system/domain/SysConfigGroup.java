package com.cenhai.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName sys_config_group
 */
@TableName(value ="sys_config_group")
@Data
public class SysConfigGroup implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long groupId;

    /**
     * 组名称
     */
    private String name;

    /**
     * 组图标
     */
    private String icon;

    /**
     * 备注
     */
    private String remark;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}