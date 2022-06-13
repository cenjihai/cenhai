package com.cenhai.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cenhai.system.domain.SysRole;
import com.cenhai.system.param.RoleQueryParam;
import com.cenhai.system.service.SysRoleService;
import com.cenhai.system.mapper.SysRoleMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author a
* @description 针对表【sys_role】的数据库操作Service实现
* @createDate 2022-05-09 11:49:01
*/
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
    implements SysRoleService{

    /**
     * 查询指定用户拥有的角色
     *
     * @param userId
     * @return
     */
    @Override
    public List<SysRole> listByUserId(Long userId) {
        return baseMapper.listByUserId(userId);
    }

    /**
     * 查询角色列表
     *
     * @param param
     * @return
     */
    @Override
    public List<SysRole> listRole(RoleQueryParam param) {
        return baseMapper.listRole(param);
    }
}




