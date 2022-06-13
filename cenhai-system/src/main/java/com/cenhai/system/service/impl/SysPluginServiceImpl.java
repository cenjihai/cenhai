package com.cenhai.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cenhai.system.domain.SysPlugin;
import com.cenhai.system.param.PluginQueryParam;
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

    /**
     * 查询插件列表
     *
     * @param param
     * @return
     */
    @Override
    public List<SysPlugin> listPlugin(PluginQueryParam param) {
        return baseMapper.listPlugin(param);
    }

    /**
     * 改变状态
     *
     * @param pluginId
     * @param status
     * @return
     */
    @Override
    public boolean changeStatus(String pluginId, String status) {
        SysPlugin plugin = new SysPlugin();
        plugin.setPluginId(pluginId);
        plugin.setStatus(status);
        return updateById(plugin);
    };
}




