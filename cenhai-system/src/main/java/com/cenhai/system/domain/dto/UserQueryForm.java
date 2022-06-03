package com.cenhai.system.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 后台管理-用户列表-用户数据表格-表头查询表单
 */
@Data
public class UserQueryForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private String nickname;

    private String username;

    private String sex;

    private String startTime;

    private String endTime;
}
