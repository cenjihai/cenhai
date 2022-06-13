package com.cenhai.system.mapper;

import com.cenhai.system.domain.SysPlugin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cenhai.system.param.PluginQueryParam;

import java.util.List;

/**
* @author a
* @description 针对表【sys_plugin(插件表)】的数据库操作Mapper
* @createDate 2022-06-04 11:20:48
* @Entity com.cenhai.system.domain.SysPlugin
*/
public interface SysPluginMapper extends BaseMapper<SysPlugin> {

    List<SysPlugin> listPlugin(PluginQueryParam param);

}




