package com.cenhai.system.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SimpleConfigForm implements Serializable {

    private Long configId;

    private Object configValue;

    private String widget;
}
