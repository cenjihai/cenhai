package com.cenhai.system.domain.vo;

import com.cenhai.system.domain.SysMenu;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class MenuTree implements Serializable {

    private String label;

    private Long id;

    private SysMenu field;

    private List<MenuTree> children = new ArrayList<>();

    public MenuTree(){}

    public MenuTree(SysMenu menu){
        this.label = menu.getName();
        this.id = menu.getMenuId();
        this.field = menu;
    }

    public static List<MenuTree> toMenuTree(List<SysMenu> menus){
        List<MenuTree> menuTrees = new ArrayList<>();
        for (SysMenu menu: menus) {
            menuTrees.add(new MenuTree(menu));
        }
        return menuTrees;
    }

    public static List<MenuTree> toTree(List<SysMenu> menus){
        List<MenuTree> menuTrees = toMenuTree(menus);
        List<MenuTree> root = getTreeRoot(menuTrees);
        for (MenuTree tree: root){
            buildTree(menuTrees,tree);
        }
        return root;
    }

    private static void buildTree(List<MenuTree> menuTrees, MenuTree menuTree){
        for (MenuTree tree : menuTrees){
            if (menuTree != null && menuTree.field.getMenuId().compareTo(tree.field.getParentMenuId()) == 0){
                menuTree.getChildren().add(tree);
                buildTree(menuTrees,tree);
            }
        }
    }

    private static List<MenuTree> getTreeRoot(List<MenuTree> list){
        List<MenuTree> treeList = new ArrayList<>();
        for (MenuTree menuTree: list){
            if (menuTree.field.getParentMenuId() == 0)
                treeList.add(menuTree);
        }
        return treeList;
    }
}
