package com.cenhai.system.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleQueryForm implements Serializable {

    private String name;

    private String startTime;

    private String endTime;

    private String status;

    private String roleKey;
}
