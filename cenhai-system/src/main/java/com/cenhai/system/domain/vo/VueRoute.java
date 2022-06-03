package com.cenhai.system.domain.vo;

import com.cenhai.common.constant.Constants;
import com.cenhai.system.domain.SysMenu;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class VueRoute implements Serializable {

    private String name;

    private String path;

    private String component;

    private VueRouteMeta meta;

    private boolean hidden;

    private List<VueRoute> children = new ArrayList<>();

    public VueRoute(){

    }

    public VueRoute(SysMenu menu){
        this.name = menu.getPath();
        this.path = menu.getPath();
        this.hidden = Constants.DISABLE.equals(menu.getHidden());
        this.component = menu.getComponent();
        this.meta = new VueRouteMeta(menu);
    }

    public static List<VueRoute> toVueRouter(List<SysMenu> menus){
        List<VueRoute> vueRoutes = new ArrayList<>();
        for (SysMenu menu: menus){
            vueRoutes.add(new VueRoute(menu));
        }
        return vueRoutes;
    }

    public static void buildRouterTree(List<VueRoute> vueRoutes, VueRoute vueRoute){
        for (VueRoute route : vueRoutes){
            if (vueRoute != null && vueRoute.getMeta().getMenuId().compareTo(route.getMeta().getParentId()) == 0){
                vueRoute.getChildren().add(route);
                buildRouterTree(vueRoutes,route);
            }
        }
    }

    public static List<VueRoute> getRouteTreeRoot(List<VueRoute> list){
        List<VueRoute> treeList = new ArrayList<>();
        for (VueRoute route: list){
            if (route.getMeta().getParentId() == 0)
                treeList.add(route);
        }
        return treeList;
    }
}
