package com.cenhai.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cenhai.common.constant.Constants;
import com.cenhai.common.utils.PageUtils;
import com.cenhai.common.utils.page.TableDataInfo;
import com.cenhai.common.web.controller.BaseController;
import com.cenhai.common.web.domain.Result;
import com.cenhai.framework.annotation.Log;
import com.cenhai.system.domain.SysRole;
import com.cenhai.system.param.RoleQueryParam;
import com.cenhai.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Autowired
    private SysRoleService roleService;

    /**
     * 返回用户的角色
     * @param userId
     * @return
     */
    @GetMapping("/getUserRole/{userId}")
    public Result<Map<String,Object>> listRoleOptions(@PathVariable Long userId){
        Map<String, Object> map = new HashMap<>();
        List<Long> userRoles = new ArrayList<>();
        roleService.listByUserId(userId).forEach(role -> {
            userRoles.add(role.getRoleId());
        });
        map.put("userRole", userRoles);
        map.put("allRole", roleService.list(new QueryWrapper<SysRole>().eq("status", Constants.NORMAL)));
        return Result.success(map);
    }

    @GetMapping("/list")
    public Result<TableDataInfo> list(RoleQueryParam form){
        PageUtils.startPage();
        return Result.success(PageUtils.getDataTable(roleService.listRole(form)));
    }

    @PostMapping("/updateOrSave")
    @Log(title = "角色管理",info = "更新或新增角色")
    public Result updateOrSave(@RequestBody SysRole role){
        try {
            return Result.result(roleService.saveOrUpdate(role));
        }catch (DuplicateKeyException e){
            return Result.error("角色字符不能重复");
        }
    }

    @PostMapping("/delete")
    @Log(title = "角色管理",info = "删除角色")
    public Result delete(@RequestBody Collection<Long> ids){
        return Result.result(roleService.removeByIds(ids));
    }
}
