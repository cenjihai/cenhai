package com.cenhai.system.service;

import com.cenhai.system.domain.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cenhai.system.domain.vo.VueRoute;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
* @author a
* @description 针对表【sys_menu】的数据库操作Service
* @createDate 2022-05-09 15:40:39
*/
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 查询menuPerm
     * @param userId
     * @return
     */
    List<String> listMenuPermByUserId(Long userId);

    /**
     * 根据用户id，列出路由
     * @param userId
     * @return
     */
    List<VueRoute> listVueRoutesByUserId(Long userId);

    /**
     * 删除菜单，只允许从下往上删除
     * @param menuId
     * @return
     */
    boolean deleteMenuByMenuId(Long menuId);

    /**
     * 返回选中和全部的菜单
     * @param roleId
     * @return
     */
    Map<String,Object> listSelectedAndAllMenuByRoleId(Long roleId);

    /**
     * 菜单分配给角色
     * @param roleId
     * @param menuIds
     * @return
     */
    boolean assignToRoleByRoleId(Long roleId, Collection<Long> menuIds);
}
