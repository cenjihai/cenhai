package com.cenhai.admin.controller;

import com.cenhai.common.utils.PageUtils;
import com.cenhai.common.utils.StringUtils;
import com.cenhai.common.utils.page.TableDataInfo;
import com.cenhai.common.web.controller.BaseController;
import com.cenhai.common.web.domain.Result;
import com.cenhai.framework.annotation.Log;
import com.cenhai.system.param.SimpleUserParam;
import com.cenhai.system.param.UserQueryParam;
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


    /**
     * 返回用户列表
     * @param param
     * @return
     */
    @GetMapping("/list")
    public Result<TableDataInfo> list(UserQueryParam param){
        PageUtils.startPage();
        return Result.success(PageUtils.getDataTable(userService.listUser(param)));
    }

    /**
     * 新增或更新用户信息
     * @param param
     * @return
     */
    @PostMapping("/updateOrSave")
    @Log(title = "用户管理",info = "更新或者新增用户")
    public Result updateOrSave(@RequestBody @Valid SimpleUserParam param){
        //如果是新增切未设置头像，则使用默认头像
        if (StringUtils.isNull(param.getHeadimgurl()) && StringUtils.isNotNull(param.getUserId()))
            param.setHeadimgurl(configService.getConfigValue("default-headimg").toString());
        return Result.result(userService.saveOrUpdate(param.toSysUser()));
    }

    /**
     * 批量删除用户
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    @Log(title = "用户管理",info = "删除用户")
    public Result delete(@RequestBody Collection<Long> ids){
        return Result.result(userService.removeBatchByIds(ids));
    }

    /**
     * 更新用户的角色
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
