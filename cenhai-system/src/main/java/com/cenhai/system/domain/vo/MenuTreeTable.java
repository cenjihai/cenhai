package com.cenhai.system.domain.vo;

import com.cenhai.system.domain.SysMenu;
import lombok.Data;

import java.io.Serializable;

@Data
public class MenuTreeTable implements Serializable {

    /**
     *
     */
    private Long menuId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单地址
     */
    private String path;

    /**
     * 状态
     */
    private String status;

    /**
     * 排序
     */
    private Integer menuOrder;

    /**
     * 图标
     */
    private String icon;

    /**
     * 组件
     */
    private String component;

    /**
     * 是否隐藏
     */
    private String hidden;

    /**
     * 权限
     */
    private String permKey;

    /**
     * 类型 M菜单，P权限
     */
    private String type;

    /**
     * 是否有子菜单
     */
    private boolean hasChildren;


    public MenuTreeTable(){}

    public MenuTreeTable(SysMenu menu) {
        this.menuId = menu.getMenuId();
        this.name = menu.getName();
        this.path = menu.getPath();
        this.status = menu.getStatus();
        this.menuOrder = menu.getMenuOrder();
        this.icon = menu.getIcon();
        this.component = menu.getComponent();
        this.hidden = menu.getHidden();
        this.permKey = menu.getPermKey();
        this.type = menu.getType();
        this.hasChildren = !"P".equals(menu.getType());
    }
}
