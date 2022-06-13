package com.cenhai.system.service;

import com.cenhai.system.domain.SysUserAuth;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cenhai.system.param.SimpleUpdatePasswordParam;


/**
* @author a
* @description 针对表【sys_user_auth】的数据库操作Service
* @createDate 2022-05-09 11:49:01
*/
public interface SysUserAuthService extends IService<SysUserAuth> {

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
    boolean saveOrUpdateUserAuthByPassword(SysUserAuth userAuth);

    /**
     * 更新密码认证方式，必须填入用户ID
     * @param param
     * @return
     */
    boolean updatePasswordByUserId(SimpleUpdatePasswordParam param);
}
