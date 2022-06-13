package com.cenhai.common.web.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 基础搜索参数
 */
@Data
public class BaseParam implements Serializable {

    private String startTime;

    private String endTime;
}
