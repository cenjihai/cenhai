package com.cenhai.admin.event;

import com.cenhai.common.constant.Constants;
import com.cenhai.system.service.SysPluginService;
import com.gitee.starblues.core.PluginInfo;
import com.gitee.starblues.integration.listener.PluginListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class DefaultPluginListener implements PluginListener {

    private static final Logger log = LoggerFactory.getLogger(DefaultPluginListener.class);

    @Autowired
    private SysPluginService pluginService;

    @Override
    public void loadSuccess(PluginInfo pluginInfo) {
        log.info("插件[{}]加载成功.", pluginInfo.getPluginId());
    }

    @Override
    public void loadFailure(Path path, Throwable throwable) {
        log.info("插件[{}]加载失败. {}", path, throwable.getMessage());
    }

    @Override
    public void unLoadSuccess(PluginInfo pluginInfo) {
        log.info("插件[{}]卸载成功", pluginInfo.getPluginId());
        pluginService.removeById(pluginInfo.getPluginId());
    }

    @Override
    public void unLoadFailure(PluginInfo pluginInfo, Throwable throwable) {
        log.info("插件[{}]卸载失败. {}", pluginInfo.getPluginId(), throwable.getMessage());
    }

    @Override
    public void startSuccess(PluginInfo pluginInfo) {
        log.info("插件[{}]启动成功", pluginInfo.getPluginId());
        pluginService.changeStatus(pluginInfo.getPluginId(), Constants.NORMAL);
    }

    @Override
    public void startFailure(PluginInfo pluginInfo, Throwable throwable) {
        log.info("插件[{}]启动失败. {}", pluginInfo.getPluginId(), throwable.getMessage());
        pluginService.changeStatus(pluginInfo.getPluginId(), Constants.DISABLE);
    }

    @Override
    public void stopSuccess(PluginInfo pluginInfo) {
        log.info("插件[{}]停止成功", pluginInfo.getPluginId());
        pluginService.changeStatus(pluginInfo.getPluginId(), Constants.DISABLE);
    }

    @Override
    public void stopFailure(PluginInfo pluginInfo, Throwable throwable) {
        log.info("插件[{}]停止失败. {}", pluginInfo.getPluginId(), throwable.getMessage());
    }
}
