package com.cenhai.system.mapper;

import com.cenhai.system.domain.SysUserAuth;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
* @author a
* @description 针对表【sys_user_auth】的数据库操作Mapper
* @createDate 2022-05-09 11:49:01
* @Entity com.cenhai.system.domain.SysUserAuth
*/
public interface SysUserAuthMapper extends BaseMapper<SysUserAuth> {

    SysUserAuth getByUserIdAndIdentityType(@Param("userId")Long userId, @Param("identityType") String identityType);

    List<SysUserAuth> listByUserId(@Param("userId") Long userId);

    int batchDeleteByUserIds(Collection<Long> ids);
}




