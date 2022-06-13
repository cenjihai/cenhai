package com.cenhai.system.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class SimpleUpdateConfigParam implements Serializable {

    private Long configId;

    private Object configValue;

    private String widget;
}
