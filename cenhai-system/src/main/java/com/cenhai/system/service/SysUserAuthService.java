package com.cenhai.system.service;

import com.cenhai.common.constant.IdentityType;
import com.cenhai.system.domain.SysUserAuth;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cenhai.system.domain.dto.SimplePasswordForm;

import java.util.Collection;
import java.util.Map;

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
    SysUserAuth getUserAuthByIdentifierAndIdentityType(String identifier, IdentityType identityType);

    /**
     * 获取用户已经设置的所有认证信息
     * @param userId
     * @return
     */
    Map<String,SysUserAuth> listUserAuthInfoByUserId(Long userId);

    /**
     * 获取用户认证方式信息
     * @param userId 用户ID
     * @param identityType 认证方式
     * @return
     */
    SysUserAuth getByUserIdAndIdentityType(Long userId, IdentityType identityType);

    /**
     * 批量删除
     * @param ids 用户id 列表
     * @return
     */
    int batchDeleteByUserIds(Collection<Long> ids);

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
