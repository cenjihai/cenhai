package com.cenhai.system.service;

import com.cenhai.system.domain.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cenhai.system.domain.vo.MenuTree;
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
     * 根据权限id查询菜单，有缓存
     * @param roleId
     * @return
     */
    List<String> listPermKeyByRoleId(Long roleId);

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
     * 返回后台菜单管理需要的树形结构
     * @param parentId
     * @return
     */
    List<MenuTree> listMenuTree(Long parentId);

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
