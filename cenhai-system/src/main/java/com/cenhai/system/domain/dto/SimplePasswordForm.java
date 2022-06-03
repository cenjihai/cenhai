package com.cenhai.system.domain.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 用户自行更新密码认证方式
 */
@Data
public class SimplePasswordForm implements Serializable {

    private Long userId;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[#?!@$%^&*-.]).{8,16}$",message = "密码必须包含大小写字母、数字、特殊符号(#?!@$%^&*-.)的8-16位组合")
    private String credential;

    private String oldCredential;
}
