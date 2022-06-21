package com.cenhai.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cenhai.common.constant.Constants;
import com.cenhai.common.utils.StringUtils;
import com.cenhai.system.domain.SysConfig;
import com.cenhai.system.param.SimpleUpdateConfigParam;
import com.cenhai.system.service.SysConfigService;
import com.cenhai.system.mapper.SysConfigMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
* @author cenjihai
* @description 针对表【sys_config(系统配置表)】的数据库操作Service实现
* @createDate 2022-05-29 13:31:47
*/
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig>
    implements SysConfigService{


    /**
     * 批量更新配置信息。来源后台管理提交的
     *
     * @param configForms
     * @return
     */
    @Override
    @Transactional
    @CacheEvict(value = "config", allEntries = true)
    public boolean batchUpdateByAdmin(List<SimpleUpdateConfigParam> configForms) {
        List<SysConfig> configs = new ArrayList<>();
        for (SimpleUpdateConfigParam form : configForms){
            SysConfig config = new SysConfig();
            config.setConfigId(form.getConfigId());
            if (form.getWidget().equals("checkbox")){
                config.setConfigValue(JSONObject.toJSONString(form.getConfigValue()));
            }else {
                config.setConfigValue(form.getConfigValue().toString());
            }
            configs.add(config);
        }
        return updateBatchById(configs);
    }

    /**
     * 获取一个配置信息。有缓存
     *
     * @param configKey
     * @return
     */
    @Override
    @Cacheable(value = "config", key = "#configKey")
    public Object getConfigValue(String configKey) {
        SysConfig config = getOne(new LambdaQueryWrapper<SysConfig>()
                .eq(SysConfig::getStatus, Constants.NORMAL)
                .eq(SysConfig::getConfigKey,configKey));
        if (StringUtils.isNull(config))return null;
        return config.getConfigValue();
    }
}




