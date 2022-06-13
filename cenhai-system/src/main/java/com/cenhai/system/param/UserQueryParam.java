package com.cenhai.system.param;

import com.cenhai.common.web.domain.BaseParam;
import lombok.Data;

/**
 * 查询用户 参数
 */
@Data
public class UserQueryParam extends BaseParam {

    private Long userId;

    private String nickname;

    private String username;

    private String sex;

}
