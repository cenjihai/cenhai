package com.cenhai.system.mapper;

import com.cenhai.system.domain.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cenhai.system.domain.dto.RoleQueryForm;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @author a
* @description 针对表【sys_role】的数据库操作Mapper
* @createDate 2022-05-09 11:49:01
* @Entity com.cenhai.system.domain.SysRole
*/
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> listByUserId(@Param("userId") Long userId);

    List<SysRole> listRole(RoleQueryForm form);

    List<SysRole> listRoleByPermId(@Param("permId") Long permId);

    List<SysRole> listRoleIdAndRoleKeyByUserId(@Param("userId") Long userId);
}




