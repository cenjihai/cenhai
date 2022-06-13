package com.cenhai.system.param;

import com.cenhai.common.web.domain.BaseParam;
import lombok.Data;


@Data
public class RoleQueryParam extends BaseParam {

    private String name;

    private String status;

    private String roleKey;
}
