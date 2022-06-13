package com.cenhai.system.service;

import com.cenhai.system.domain.SysConfig;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cenhai.system.param.SimpleUpdateConfigParam;

import java.util.List;

/**
* @author cenjihai
* @description 针对表【sys_config(系统配置表)】的数据库操作Service
* @createDate 2022-05-29 13:31:47
*/
public interface SysConfigService extends IService<SysConfig> {

    /**
     * 批量更新配置信息。来源后台管理提交的
     * @param configForms
     * @return
     */
    boolean batchUpdateByAdmin(List<SimpleUpdateConfigParam> configForms);

    /**
     * 获取一个配置信息。有缓存
     * @param configKey
     * @return
     */
    Object getConfigValue(String configKey);
}
