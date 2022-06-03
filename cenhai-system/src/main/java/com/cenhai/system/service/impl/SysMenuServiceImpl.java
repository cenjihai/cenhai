package com.cenhai.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cenhai.common.exception.ServiceException;
import com.cenhai.common.utils.StringUtils;
import com.cenhai.system.domain.SysMenu;
import com.cenhai.system.domain.SysRoleMenu;
import com.cenhai.system.domain.vo.MenuTree;
import com.cenhai.system.domain.vo.VueRoute;
import com.cenhai.system.service.SysMenuService;
import com.cenhai.system.mapper.SysMenuMapper;
import com.cenhai.system.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
* @author a
* @description 针对表【sys_menu】的数据库操作Service实现
* @createDate 2022-05-09 15:40:39
*/
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
    implements SysMenuService{

    @Autowired
    private SysRoleMenuService roleMenuService;



    @Override
    @CacheEvict(value = "role_permKey", allEntries = true)
    public boolean saveOrUpdate(SysMenu entity) {
        return super.saveOrUpdate(entity);
    }

    /**
     * 根据权限id查询菜单，有缓存
     *
     * @param roleId
     * @return
     */
    @Override
    @Cacheable(value = "role_permKey",key = "#roleId")
    public List<String> listPermKeyByRoleId(Long roleId) {
        return baseMapper.listPermKeyByRoleId(roleId);
    }

    /**
     * 根据用户id，列出路由
     * 用于后台管理，左侧菜单和路由设置
     * @param userId
     * @return
     */
    @Override
    public List<VueRoute> listVueRoutesByUserId(Long userId) {
        List<SysMenu> menus = baseMapper.listByUserId(userId);
        List<VueRoute> routes = VueRoute.toVueRouter(menus);
        List<VueRoute> root = VueRoute.getRouteTreeRoot(routes);
        for (VueRoute route : root){
            VueRoute.buildRouterTree(routes,route);
        }
        return root;
    }

    /**
     * 删除菜单，只允许从下往上删除
     *
     * @param menuId
     * @return
     */
    @Override
    @CacheEvict(value = "role_permKey", allEntries = true)
    public boolean deleteMenuByMenuId(Long menuId) {
        QueryWrapper<SysMenu> wrapper = new QueryWrapper();
        wrapper.eq("parent_menu_id",menuId);
        int count = count(wrapper);
        if (count > 0)throw new ServiceException("请先删除当前项的子项");
        return removeById(menuId);
    }

    /**
     * 返回后台菜单管理需要的树形结构
     *
     * @return
     */
    @Override
    public List<MenuTree> listMenuTree(Long parentId) {
        List<SysMenu> menus = list(new QueryWrapper<SysMenu>()
                .orderByAsc("menu_order")
                .eq("parent_menu_id",parentId)
        );
        List<MenuTree> menuTrees = MenuTree.toMenuTree(menus);
        return menuTrees;
    }

    /**
     * 返回选中和全部的菜单
     *
     * @param roleId
     * @return
     */
    @Override
    public Map<String, Object> listSelectedAndAllMenuByRoleId(Long roleId) {
        Map<String,Object> result = new HashMap<>();
        result.put("selected",baseMapper.listMenuIdByRoleId(roleId));
        List<SysMenu> menus = list(new QueryWrapper<SysMenu>()
                .orderByDesc("menu_order")
        );
        result.put("all", MenuTree.toTree(menus));
        return result;
    }

    /**
     * 菜单分配给角色
     *
     * @param roleId
     * @param menuIds
     * @return
     */
    @Override
    @Transactional
    @CacheEvict(value = "role_permKey", allEntries = true)
    public boolean assignToRoleByRoleId(Long roleId, Collection<Long> menuIds) {
        roleMenuService.remove(new QueryWrapper<SysRoleMenu>().eq("role_id",roleId));
        if (StringUtils.isEmpty(menuIds))return true;
        List<SysRoleMenu> roleMenus = new ArrayList<>();
        for (Long id: menuIds){
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(id);
            roleMenus.add(roleMenu);
        }
        return roleMenuService.saveBatch(roleMenus);
    }
}




