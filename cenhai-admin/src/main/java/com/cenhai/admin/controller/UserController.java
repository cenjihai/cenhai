package com.cenhai.admin.controller;

import com.cenhai.common.utils.PageUtils;
import com.cenhai.common.utils.page.TableDataInfo;
import com.cenhai.common.web.controller.BaseController;
import com.cenhai.common.web.domain.Result;
import com.cenhai.framework.annotation.Log;
import com.cenhai.system.domain.dto.SimpleUserForm;
import com.cenhai.system.domain.dto.UserQueryForm;
import com.cenhai.system.service.SysConfigService;
import com.cenhai.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysConfigService configService;


    @GetMapping("/list")
    public Result<TableDataInfo> list(UserQueryForm form){
        PageUtils.startPage();
        return Result.success(PageUtils.getDataTable(userService.listUserAndUserAuth(form)));
    }

    @PostMapping("/updateOrSave")
    @Log(title = "用户管理",info = "更新或者新增用户")
    public Result updateOrSave(@RequestBody @Valid SimpleUserForm userForm){
        return Result.result(userService.saveOrUpdate(userForm.toUser(configService.getConfigValue("default-headimg").toString())));
    }

    @PostMapping("/delete")
    @Log(title = "用户管理",info = "删除用户")
    public Result delete(@RequestBody Collection<Long> ids){
        return Result.result(userService.deleteByUserIds(ids));
    }

    /**
     * 更新指导用户的角色
     * @param roleIds
     * @param userId
     * @return
     */
    @PostMapping("/updateRole/{userId}")
    @Log(title = "用户管理",info = "更新用户的角色")
    public Result updateRole(@RequestBody Collection<Long> roleIds, @PathVariable Long userId){
        return Result.result(userService.updateUserRoleByUserId(userId, roleIds));
    }
}
