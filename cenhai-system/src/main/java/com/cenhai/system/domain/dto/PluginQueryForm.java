package com.cenhai.system.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PluginQueryForm implements Serializable {

    private String pluginId;

    private String name;

    private String status;

    private String startTime;

    private String endTime;
}
