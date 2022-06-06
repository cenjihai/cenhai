package com.cenhai.system.service;

import com.cenhai.system.domain.SysUserAuth;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cenhai.system.domain.dto.SimplePasswordForm;


/**
* @author a
* @description 针对表【sys_user_auth】的数据库操作Service
* @createDate 2022-05-09 11:49:01
*/
public interface SysUserAuthService extends IService<SysUserAuth> {

    /**
     * 根据身份名称和身份类型获取用户认证信息
     * @param identifier
     * @return
     */
    SysUserAuth getUserAuthByIdentifierAndIdentityType(String identifier, String identityType);

    /**
     * 获取密码认证方式的信息
     * @param userId 用户ID
     * @return
     */
    SysUserAuth getPasswordTypeByUserId(Long userId);

    /**
     * 更新或新增 密码认证方式
     * @param userAuth
     * @return
     */
    boolean updateOrSaveUserAuthByPassword(SysUserAuth userAuth);

    /**
     * 更新密码认证方式，必须填入用户ID
     * @param form
     * @return
     */
    boolean updateUserAuthByPasswordAndUserId(SimplePasswordForm form);
}
