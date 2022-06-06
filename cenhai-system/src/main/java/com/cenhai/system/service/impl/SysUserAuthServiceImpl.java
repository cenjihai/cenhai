package com.cenhai.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cenhai.common.constant.Constants;
import com.cenhai.common.exception.ServiceException;
import com.cenhai.common.utils.StringUtils;
import com.cenhai.system.domain.SysUserAuth;
import com.cenhai.system.domain.dto.SimplePasswordForm;
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
     * 根据身份名称和身份类型获取用户认证信息
     *
     * @param identifier
     * @param identityType
     * @return
     */
    @Override
    public SysUserAuth getUserAuthByIdentifierAndIdentityType(String identifier, String identityType) {
        return getOne(new QueryWrapper<SysUserAuth>()
                .eq("identity_type",identityType)
                .eq("identifier", identifier));
    }


    @Override
    public SysUserAuth getPasswordTypeByUserId(Long userId) {
        return getOne(new QueryWrapper<SysUserAuth>()
                .eq("user_id",userId)
                .eq("identity_type",Constants.DEFAULT_SECURITY_IDENTITY_TYPE));
    }

    /**
     * 更新或新增 密码认证方式
     *
     * @param userAuth
     * @return
     */
    @Override
    public boolean updateOrSaveUserAuthByPassword(SysUserAuth userAuth) {
        userAuth.setIdentityType(Constants.DEFAULT_SECURITY_IDENTITY_TYPE);
        if (StringUtils.isEmpty(userAuth.getCredential())){
            userAuth.setCredential(null);
        }else {
            userAuth.setCredential(new BCryptPasswordEncoder().encode(userAuth.getCredential()));
        }
        if (StringUtils.isNull(userAuth.getAuthId())){
            userAuth.setVerified(Constants.YES);
            try {
                return save(userAuth);
            }catch (DuplicateKeyException e){
                throw new ServiceException("用户名已存在");
            }
        }else {
            try {
                return updateById(userAuth);
            }catch (DuplicateKeyException e){
                throw new ServiceException("用户名已存在");
            }
        }
    }

    /**
     * 更新密码认证方式，必须填入用户ID
     *
     * @param form
     * @return
     */
    @Override
    public boolean updateUserAuthByPasswordAndUserId(SimplePasswordForm form) {
        SysUserAuth userAuth = getPasswordTypeByUserId(form.getUserId());
        if (StringUtils.isNull(userAuth))throw new ServiceException("未开启密码认证方式!");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(form.getOldCredential(),userAuth.getCredential()))
            throw new ServiceException("原密码错误");
        userAuth.setCredential(passwordEncoder.encode(form.getCredential()));
        return updateById(userAuth);
    }

}




