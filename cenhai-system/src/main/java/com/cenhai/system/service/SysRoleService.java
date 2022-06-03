package com.cenhai.system.service;

import com.cenhai.system.domain.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cenhai.system.domain.dto.RoleQueryForm;

import java.util.List;
import java.util.Map;

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


    List<SysRole> listRole(RoleQueryForm form);

    /**
     * 查询角色ID和角色Key
     * @param userId
     * @return
     */
    List<SysRole> listRoleIdAndRoleKeyByUserId(Long userId);
}
