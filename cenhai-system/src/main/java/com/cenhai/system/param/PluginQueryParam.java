package com.cenhai.system.param;

import com.cenhai.common.web.domain.BaseParam;
import lombok.Data;


@Data
public class PluginQueryParam extends BaseParam {

    private String pluginId;

    private String name;

    private String status;
}
