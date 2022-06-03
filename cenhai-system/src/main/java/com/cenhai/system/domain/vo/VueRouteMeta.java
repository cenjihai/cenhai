package com.cenhai.system.domain.vo;

import com.cenhai.system.domain.SysMenu;
import lombok.Data;

import java.io.Serializable;

@Data
public class VueRouteMeta implements Serializable {

    private String title;

    private String icon;

    private Long menuId;

    private Long parentId;

    public VueRouteMeta(){}

    public VueRouteMeta(SysMenu menu){
        this.title = menu.getName();
        this.icon = menu.getIcon();
        this.parentId = menu.getParentMenuId();
        this.menuId = menu.getMenuId();
    }
}
