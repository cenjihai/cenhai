package com.cenhai.system.mapper;

import com.cenhai.system.domain.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author a
* @description 针对表【sys_menu】的数据库操作Mapper
* @createDate 2022-05-09 15:40:39
* @Entity com.cenhai.system.domain.SysMenu
*/
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> listByUserId(@Param("userId") Long userId);

    List<Long> listMenuIdByRoleId(@Param("roleId") Long roleId);

    List<String> listMenuPermByUserId(@Param("userId")Long userId);
}




