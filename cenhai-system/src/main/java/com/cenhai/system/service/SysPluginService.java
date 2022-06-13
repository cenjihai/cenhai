package com.cenhai.system.service;

import com.cenhai.system.domain.SysPlugin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cenhai.system.param.PluginQueryParam;

import java.util.List;

/**
* @author a
* @description 针对表【sys_plugin(插件表)】的数据库操作Service
* @createDate 2022-06-04 11:20:48
*/
public interface SysPluginService extends IService<SysPlugin> {

    /**
     * 查询插件列表
     * @param param
     * @return
     */
    List<SysPlugin> listPlugin(PluginQueryParam param);

    /**
     * 改变状态
     * @param pluginId
     * @param status
     * @return
     */
    boolean changeStatus(String pluginId, String status);
}
