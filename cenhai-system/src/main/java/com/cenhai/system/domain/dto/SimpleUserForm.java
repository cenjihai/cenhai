package com.cenhai.system.domain.dto;

import com.cenhai.common.utils.StringUtils;
import com.cenhai.system.domain.SysUser;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 简单的添加用户表单
 */
@Data
public class SimpleUserForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;

    @NotBlank(message = "昵称不能为空")
    @Length(max = 16, min = 3,message = "昵称格式不正确")
    private String nickname;

    @Pattern(regexp = "[0|1|2]",message = "性别错误")
    private String sex;

    private String country;

    private String province;

    private String city;

    @Length(max = 85, min = 0,message = "备注最大85个中文字符")
    private String remark;

    private String headimgurl;

    public SysUser toUser(String defaultHeadimgurl){
        SysUser user = new SysUser();
        user.setNickname(this.nickname);
        user.setSex(this.sex);
        user.setCountry(this.country);
        user.setProvince(this.province);
        user.setCity(this.city);
        user.setRemark(this.remark);
        if (StringUtils.isNull(this.headimgurl))
            this.headimgurl = defaultHeadimgurl;
        user.setHeadimgurl(this.headimgurl);
        user.setUserId(this.userId);
        return user;
    }
}
