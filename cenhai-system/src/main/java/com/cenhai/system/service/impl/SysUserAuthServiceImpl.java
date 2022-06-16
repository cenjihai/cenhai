package com.cenhai.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cenhai.common.constant.Constants;
import com.cenhai.common.exception.ApiException;
import com.cenhai.common.utils.StringUtils;
import com.cenhai.system.domain.SysUserAuth;
import com.cenhai.system.param.SimpleUpdatePasswordParam;
import com.cenhai.system.service.SysUserAuthService;
import com.cenhai.system.mapper.SysUserAuthMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
* @author a
* @description 针对表【sys_user_auth】的数据库操作Service实现
* @createDate 2022-05-09 11:49:01
*/
@Service
public class SysUserAuthServiceImpl extends ServiceImpl<SysUserAuthMapper, SysUserAuth>
    implements SysUserAuthService{

    /**
     * 获取密码认证方式的数据
     * @param userId 用户ID
     * @return
     */
    @Override
    public SysUserAuth getPasswordTypeByUserId(Long userId) {
        return getOne(new LambdaQueryWrapper<SysUserAuth>()
                .eq(SysUserAuth::getUserId,userId)
                .eq(SysUserAuth::getIdentityType,Constants.DEFAULT_SECURITY_IDENTITY_TYPE));
    }

    /**
     * 更新或新增 密码认证方式
     *
     * @param userAuth
     * @return
     */
    @Override
    public boolean saveOrUpdateUserAuthByPassword(SysUserAuth userAuth) {
        //设置为密码类型
        userAuth.setIdentityType(Constants.DEFAULT_SECURITY_IDENTITY_TYPE);
        if (StringUtils.isNotEmpty(userAuth.getCredential())){
            userAuth.setCredential(new BCryptPasswordEncoder().encode(userAuth.getCredential()));
        }
        try {
            return saveOrUpdate(userAuth);
        }catch (DuplicateKeyException e){
            throw new ApiException("用户名已存在");
        }
    }

    /**
     * 更新密码认证方式，必须填入用户ID
     *
     * @param param
     * @return
     */
    @Override
    public boolean updatePasswordByUserId(SimpleUpdatePasswordParam param) {
        SysUserAuth userAuth = getPasswordTypeByUserId(param.getUserId());
        if (StringUtils.isNull(userAuth))throw new ApiException("未开启密码认证方式!");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(param.getOldCredential(),userAuth.getCredential()))
            throw new ApiException("原密码错误");
        userAuth.setCredential(passwordEncoder.encode(param.getCredential()));
        return updateById(userAuth);
    }
}




