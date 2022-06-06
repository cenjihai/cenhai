package com.cenhai.system.service;

import com.cenhai.system.domain.SysPlugin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cenhai.system.domain.dto.PluginQueryForm;

import java.util.List;

/**
* @author a
* @description 针对表【sys_plugin(插件表)】的数据库操作Service
* @createDate 2022-06-04 11:20:48
*/
public interface SysPluginService extends IService<SysPlugin> {

    List<SysPlugin> listPlugin(PluginQueryForm queryForm);

    /**
     * 卸载插件
     * @param pluginId
     * @return
     */
    boolean uninstall(String pluginId);

    /**
     * 安装
     * @param pluginId
     * @return
     */
    boolean install(String pluginId);

    /**
     * 启动
     * @param pluginId
     * @return
     */
    boolean start(String pluginId);

    /**
     * 停止
     * @param pluginId
     * @return
     */
    boolean stop(String pluginId);
}
