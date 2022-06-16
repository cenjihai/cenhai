package com.cenhai.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cenhai.common.constant.Constants;
import com.cenhai.common.web.controller.BaseController;
import com.cenhai.common.web.domain.Result;
import com.cenhai.framework.annotation.OperatedLog;
import com.cenhai.system.domain.SysConfig;
import com.cenhai.system.domain.SysConfigGroup;
import com.cenhai.system.param.SimpleUpdateConfigParam;
import com.cenhai.system.service.SysConfigGroupService;
import com.cenhai.system.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/config")
public class ConfigController extends BaseController {

    @Autowired
    private SysConfigService configService;

    @Autowired
    private SysConfigGroupService configGroupService;


    /**
     * 返回配置分组信息
     * @return
     */
    @GetMapping("/listGroup")
    public List<SysConfigGroup> listGroup(){
        return configGroupService.list();
    }

    /**
     * 返回指定分组下的所有配置信息
     * @param groupId
     * @return
     */
    @GetMapping("/listConfig/{groupId}")
    public List<SysConfig> listConfig(@PathVariable Long groupId){
        return configService.list(new QueryWrapper<SysConfig>()
                .eq("group_id",groupId)
                .eq("status",Constants.NORMAL)
                .orderByAsc("config_order"));
    }

    /**
     * 批量更新
     * @param configForms
     * @return
     */
    @PostMapping("/batchUpdate")
    @OperatedLog(title = "配置管理",info = "更新配置")
    public Result batchUpdate(@RequestBody List<SimpleUpdateConfigParam> configForms){
        if (configService.batchUpdateByAdmin(configForms))return Result.success("配置更新成功！");
            return Result.error("更新失败");
    }
}
