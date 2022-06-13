package com.cenhai.system.service;

import com.cenhai.system.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cenhai.system.param.UserQueryParam;

import java.util.Collection;
import java.util.List;

/**
* @author a
* @description 针对表【sys_user】的数据库操作Service
* @createDate 2022-05-09 11:49:01
*/
public interface SysUserService extends IService<SysUser> {

    /**
     * 返回用户列表
     * @param param
     * @return
     */
    List<SysUser> listUser(UserQueryParam param);

    /**
     * 更新用户的角色
     * @param userId
     * @param roleIds
     * @return
     */
    boolean updateUserRoleByUserId(Long userId, Collection<Long> roleIds);
}
