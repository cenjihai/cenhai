package com.cenhai.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cenhai.system.domain.SysRole;
import com.cenhai.system.domain.dto.RoleQueryForm;
import com.cenhai.system.service.SysRoleService;
import com.cenhai.system.mapper.SysRoleMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
* @author a
* @description 针对表【sys_role】的数据库操作Service实现
* @createDate 2022-05-09 11:49:01
*/
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
    implements SysRoleService{

    @Override
    public List<SysRole> listByUserId(Long userId) {
        return baseMapper.listByUserId(userId);
    }

    @Override
    public List<SysRole> listRole(RoleQueryForm form) {
        return baseMapper.listRole(form);
    }

    /**
     * 查询角色ID和角色Key
     *
     * @param userId
     * @return
     */
    @Override
    public List<SysRole> listRoleIdAndRoleKeyByUserId(Long userId) {
        return baseMapper.listRoleIdAndRoleKeyByUserId(userId);
    }

}




