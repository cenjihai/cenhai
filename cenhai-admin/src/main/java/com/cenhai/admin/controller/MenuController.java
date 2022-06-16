package com.cenhai.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cenhai.common.web.controller.BaseController;
import com.cenhai.common.web.domain.Result;
import com.cenhai.framework.annotation.OperatedLog;
import com.cenhai.system.domain.SysMenu;
import com.cenhai.system.domain.vo.MenuTreeTable;
import com.cenhai.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


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
    public List<MenuTreeTable> list(SysMenu menu){
        List<SysMenu> menus = menuService.list(new QueryWrapper<>(menu));
        List<MenuTreeTable> menuTreeTables = new ArrayList<>();
        for (SysMenu m: menus){
            menuTreeTables.add(new MenuTreeTable(m));
        }
        return menuTreeTables;
    }

    @PostMapping("/updateOrSave")
    @OperatedLog(title = "菜单管理",info = "更新菜单")
    public Object update(@RequestBody SysMenu menu){
        try {
            if (menuService.saveOrUpdate(menu)){
                return new MenuTreeTable(menu);
            }
            return Result.error("保存失败!");
        }catch (DuplicateKeyException e){
            return Result.error("菜单或目录地址已经存在!");
        }
    }

    @PostMapping("/delete/{menuId}")
    @OperatedLog(title = "配置管理",info = "删除菜单")
    public Result delete(@PathVariable Long menuId){
        if (menuService.deleteMenuByMenuId(menuId))return Result.success("删除成功");
            return Result.error("删除失败");
    }

    /**
     * 返回指定角色的已选中和全部菜单
     * @param roleId
     * @return
     */
    @GetMapping("/listSelectedAndAllMenu/{roleId}")
    public Map<String, Object> listSelectedAndAllMenu(@PathVariable Long roleId){
        return menuService.listSelectedAndAllMenuByRoleId(roleId);
    }

    /**
     * 分配菜单给角色
     * @param roleId
     * @param menuIds
     * @return
     */
    @PostMapping("/assignToRole/{roleId}")
    @OperatedLog(title = "配置管理",info = "分配菜单给角色")
    public Result assignToRole(@PathVariable Long roleId, @RequestBody Collection<Long> menuIds){
        if (menuService.assignToRoleByRoleId(roleId, menuIds))return Result.success("保存成功");
            return Result.error("保存失败");
    }
}
