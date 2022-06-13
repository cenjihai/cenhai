package com.cenhai.system.mapper;

import com.cenhai.system.domain.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cenhai.system.param.UserQueryParam;

import java.util.List;

/**
* @author a
* @description 针对表【sys_user】的数据库操作Mapper
* @createDate 2022-05-09 11:49:01
* @Entity com.cenhai.system.domain.SysUser
*/
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<SysUser> listUser(UserQueryParam param);
}




