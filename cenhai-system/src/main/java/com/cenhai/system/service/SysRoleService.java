package com.cenhai.system.service;

import com.cenhai.system.domain.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cenhai.system.param.RoleQueryParam;

import java.util.List;

/**
* @author a
* @description 针对表【sys_role】的数据库操作Service
* @createDate 2022-05-09 11:49:01
*/
public interface SysRoleService extends IService<SysRole> {

    /**
     * 查询指定用户拥有的角色
     * @param userId
     * @return
     */
    List<SysRole> listByUserId(Long userId);

    /**
     * 查询角色列表
     * @param param
     * @return
     */
    List<SysRole> listRole(RoleQueryParam param);
}
