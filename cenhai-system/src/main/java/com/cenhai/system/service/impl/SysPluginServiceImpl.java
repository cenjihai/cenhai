package com.cenhai.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cenhai.common.constant.Constants;
import com.cenhai.system.domain.SysPlugin;
import com.cenhai.system.domain.dto.PluginQueryForm;
import com.cenhai.system.service.SysPluginService;
import com.cenhai.system.mapper.SysPluginMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author a
* @description 针对表【sys_plugin(插件表)】的数据库操作Service实现
* @createDate 2022-06-04 11:20:48
*/
@Service
public class SysPluginServiceImpl extends ServiceImpl<SysPluginMapper, SysPlugin>
    implements SysPluginService{

    @Override
    public List<SysPlugin> listPlugin(PluginQueryForm queryForm) {
        return baseMapper.listPlugin(queryForm);
    }

    /**
     * 卸载插件
     *
     * @param pluginId
     * @return
     */
    @Override
    public boolean uninstall(String pluginId) {
        return removeById(pluginId);
    }

    /**
     * 安装
     *
     * @param pluginId
     * @return
     */
    @Override
    public boolean install(String pluginId) {
        SysPlugin plugin = new SysPlugin();
        plugin.setPluginId(pluginId);
        plugin.setIsInstall(Constants.YES);
        return updateById(plugin);
    }

    /**
     * 启动
     *
     * @param pluginId
     * @return
     */
    @Override
    public boolean start(String pluginId) {
        SysPlugin plugin = new SysPlugin();
        plugin.setPluginId(pluginId);
        plugin.setStatus(Constants.NORMAL);
        return updateById(plugin);
    }

    /**
     * 停止
     *
     * @param pluginId
     * @return
     */
    @Override
    public boolean stop(String pluginId) {
        SysPlugin plugin = new SysPlugin();
        plugin.setPluginId(pluginId);
        plugin.setStatus(Constants.DISABLE);
        return updateById(plugin);
    }
}




