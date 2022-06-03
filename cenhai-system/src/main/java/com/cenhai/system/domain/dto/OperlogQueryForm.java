package com.cenhai.system.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OperlogQueryForm implements Serializable {

    private Long userId;

    private String title;

    private String status;

    private String startTime;

    private String endTime;
}
