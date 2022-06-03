package com.cenhai.admin.controller;

import com.cenhai.common.constant.IdentityType;
import com.cenhai.common.web.controller.BaseController;
import com.cenhai.common.web.domain.Result;
import com.cenhai.framework.annotation.Log;
import com.cenhai.system.domain.SysUserAuth;
import com.cenhai.system.service.SysUserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/userauth")
public class UserAuthController extends BaseController {

    @Autowired
    private SysUserAuthService userAuthService;

    /**
     * 获取指定用户指定认证方式的数据
     * @param identityType
     * @param userId
     * @return
     */
    @GetMapping("/getByIdentityTypeAndUserId/{identityType}/{userId}")
    public Result<SysUserAuth> getByIdentityTypeAndUserId(@PathVariable String identityType, @PathVariable Long userId){
        return Result.success(userAuthService.getByUserIdAndIdentityType(userId, IdentityType.resolve(identityType)));
    }

    /**
     * 管理员更新用户密码认证信息
     * @param userAuth
     * @return
     */
    @PostMapping("/updateOrSaveUserAuthByPassword")
    @Log(title = "账号管理",info = "更新或设置用户账号和密码")
    public Result updateOrSaveUserAuthByPassword(@RequestBody SysUserAuth userAuth){
        return Result.result(userAuthService.updateOrSaveUserAuthByPassword(userAuth));
    }
}
