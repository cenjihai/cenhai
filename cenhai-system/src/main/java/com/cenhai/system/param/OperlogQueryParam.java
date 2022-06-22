package com.cenhai.system.param;

import com.cenhai.common.web.domain.BaseParam;
import lombok.Data;


@Data
public class OperlogQueryParam extends BaseParam {

    private Long userId;

    private String operType;

    private String status;
}
