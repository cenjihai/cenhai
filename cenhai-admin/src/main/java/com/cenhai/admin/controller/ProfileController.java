package com.cenhai.admin.controller;

import com.cenhai.common.utils.HeadimgUtils;
import com.cenhai.common.utils.StringUtils;
import com.cenhai.common.web.controller.BaseController;
import com.cenhai.common.web.domain.Result;
import com.cenhai.framework.annotation.Log;
import com.cenhai.framework.config.ServerConfig;
import com.cenhai.framework.security.SecurityUtils;
import com.cenhai.system.domain.SysUserAuth;
import com.cenhai.system.param.SimpleUpdatePasswordParam;
import com.cenhai.system.param.SimpleUserParam;
import com.cenhai.system.service.SysConfigService;
import com.cenhai.system.service.SysUserAuthService;
import com.cenhai.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户自己信息的管理控制器。
 * 请勿防止管理系统的方法
 */
@RestController
@RequestMapping("/profile")
public class ProfileController extends BaseController {

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysUserAuthService userAuthService;

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private SysConfigService configService;

    /**
     * 获取自己的用户信息
     * @return
     */
    @GetMapping("/getInfo")
    public Result<Map<String,Object>> getInfo(){
        Long userId = SecurityUtils.getUserId();
        Map<String,Object> data = new HashMap<>();
        data.put("userInfo", userService.getById(userId));
        data.put("permissions", SecurityUtils.getPerms());
        return Result.success(data);
    }

    /**
     * 更新个人信息
     * @param param
     * @return
     */
    @PostMapping("/update")
    @Log(title = "个人信息",info = "更新个人信息")
    public Result update(@RequestBody @Valid SimpleUserParam param){
        param.setUserId(SecurityUtils.getUserId());
        //不允许用户自己更新备注信息
        param.setRemark(null);
        //判断头像处理
        String defaultHeadimgurl = configService.getConfigValue("default-headimg").toString();
        if (!StringUtils.ishttp(param.getHeadimgurl()) && !param.getHeadimgurl().equals(defaultHeadimgurl)){
            param.setHeadimgurl(HeadimgUtils.saveBase64ToLocalImage(param.getHeadimgurl(),serverConfig.getUrl()));
        }
        if (StringUtils.isNull(param.getHeadimgurl()))param.setHeadimgurl(defaultHeadimgurl);
        return Result.result(userService.updateById(param.toSysUser()));
    }

    /**
     * 更新密码认证方式
     * 就是修改密码
     * @param param
     * @return
     */
    @PostMapping("/updateUserAuthByPassword")
    @Log(title = "个人信息",info = "修改密码")
    public Result updateUserAuthByPassword(@RequestBody @Valid SimpleUpdatePasswordParam param){
        Long userId = SecurityUtils.getUserId();
        param.setUserId(userId);
        return Result.success(userAuthService.updatePasswordByUserId(param));
    }

    /**
     * 获取自己认证方式数据
     * 修改密码用到
     * @return
     */
    @GetMapping("/getUserAuthInfo")
    public Result<SysUserAuth> getUserAuthInfo(){
        return Result.success(userAuthService.getPasswordTypeByUserId(SecurityUtils.getUserId()));
    }
}
