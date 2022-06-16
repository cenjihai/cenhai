package com.cenhai.admin.controller;

import com.cenhai.common.web.controller.BaseController;
import com.cenhai.framework.security.SecurityUtils;
import com.cenhai.system.domain.vo.VueRoute;
import com.cenhai.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class LoginController extends BaseController {


    @Autowired
    private SysMenuService menuService;

    /**
     * 返回前端需要的路由信息
     * @return
     */
    @GetMapping("/listVueRoute")
    public List<VueRoute> listVueRoute(){
        Long userId = SecurityUtils.getUserId();
        return menuService.listVueRoutesByUserId(userId);
    }
}
