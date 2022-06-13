package com.cenhai.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cenhai.system.domain.SysUser;
import com.cenhai.system.domain.SysUserRole;
import com.cenhai.system.param.UserQueryParam;
import com.cenhai.system.service.SysUserRoleService;
import com.cenhai.system.service.SysUserService;
import com.cenhai.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
* @author a
* @description 针对表【sys_user】的数据库操作Service实现
* @createDate 2022-05-09 11:49:01
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{

    @Autowired
    private SysUserRoleService userRoleService;

    @Override
    public List<SysUser> listUser(UserQueryParam param) {
        return baseMapper.listUser(param);
    }

    @Override
    @Transactional
    public boolean updateUserRoleByUserId(Long userId, Collection<Long> roleIds) {
        userRoleService.remove(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId,userId));
        List<SysUserRole> userRoles = new ArrayList<>();
        for (Long id : roleIds){
            SysUserRole userRole = new SysUserRole();
            userRole.setRoleId(id);
            userRole.setUserId(userId);
            userRoles.add(userRole);
        }
        userRoleService.saveBatch(userRoles);
        return true;
    }
}




