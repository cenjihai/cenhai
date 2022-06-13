package com.cenhai.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cenhai.common.web.controller.BaseController;
import com.cenhai.common.web.domain.Result;
import com.cenhai.framework.annotation.Log;
import com.cenhai.system.domain.SysMenu;
import com.cenhai.system.domain.vo.MenuTreeTable;
import com.cenhai.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController {

    @Autowired
    private SysMenuService menuService;

    /**
     * 返回菜单列表
     * @param menu
     * @return
     */
    @GetMapping("/list")
    public Result<List<MenuTreeTable>> list(SysMenu menu){
        List<SysMenu> menus = menuService.list(new QueryWrapper<>(menu));
        List<MenuTreeTable> menuTreeTables = new ArrayList<>();
        for (SysMenu m: menus){
            menuTreeTables.add(new MenuTreeTable(m));
        }
        return Result.success(menuTreeTables);
    }

    @PostMapping("/updateOrSave")
    @Log(title = "菜单管理",info = "更新菜单")
    public Result update(@RequestBody SysMenu menu){
        try {
            if (menuService.saveOrUpdate(menu)){
                return Result.success(new MenuTreeTable(menu));
            }
            return Result.result(false);
        }catch (DuplicateKeyException e){
            return Result.error("菜单或目录地址已经存在!");
        }
    }

    @PostMapping("/delete/{menuId}")
    @Log(title = "配置管理",info = "删除菜单")
    public Result delete(@PathVariable Long menuId){
        return Result.result(menuService.deleteMenuByMenuId(menuId));
    }

    /**
     * 返回指定角色的已选中和全部菜单
     * @param roleId
     * @return
     */
    @GetMapping("/listSelectedAndAllMenu/{roleId}")
    public Result listSelectedAndAllMenu(@PathVariable Long roleId){
        return Result.success(menuService.listSelectedAndAllMenuByRoleId(roleId));
    }

    /**
     * 分配菜单给角色
     * @param roleId
     * @param menuIds
     * @return
     */
    @PostMapping("/assignToRole/{roleId}")
    @Log(title = "配置管理",info = "分配菜单给角色")
    public Result assignToRole(@PathVariable Long roleId, @RequestBody Collection<Long> menuIds){
        return Result.result(menuService.assignToRoleByRoleId(roleId, menuIds));
    }
}
