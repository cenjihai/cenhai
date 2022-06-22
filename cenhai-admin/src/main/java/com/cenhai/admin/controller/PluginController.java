package com.cenhai.admin.controller;

import com.cenhai.common.constant.Constants;
import com.cenhai.common.utils.page.PageUtils;
import com.cenhai.common.utils.page.TableDataInfo;
import com.cenhai.common.web.controller.BaseController;
import com.cenhai.common.web.domain.Result;
import com.cenhai.framework.annotation.Log;
import com.cenhai.system.domain.SysPlugin;
import com.cenhai.system.param.PluginQueryParam;
import com.cenhai.system.service.SysPluginService;
import com.gitee.starblues.core.PluginInfo;
import com.gitee.starblues.core.exception.PluginException;
import com.gitee.starblues.integration.operator.PluginOperator;
import com.gitee.starblues.integration.operator.upload.UploadParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/plugin")
public class PluginController extends BaseController {

    @Autowired
    private SysPluginService pluginService;

    @Autowired
    private PluginOperator pluginOperator;

    /**
     * 查询
     * @param param
     * @return
     */
    @GetMapping("/list")
    public TableDataInfo list(PluginQueryParam param){
        PageUtils.startPage();
        return PageUtils.getDataTable(pluginService.listPlugin(param));
    }

    /**
     * 卸载插件
     * @param pluginId
     * @return
     */
    @PostMapping("/uninstall/{pluginId}")
    @Log(operType = "插件管理", desc = "'卸载ID为[' + #pluginId + ']的插件'")
    public Result uninstall(@PathVariable String pluginId){
        try {
            pluginOperator.uninstall(pluginId, true, true);
            return Result.success("卸载成功-插件ID: " + pluginId);
        }catch (PluginException e){
            return Result.error("卸载失败-插件ID: " + pluginId);
        }
    }

    /**
     * 上传并安装插件
     * @param multipartFile
     * @return
     */
    @PostMapping("/upload")
    @Log(operType = "插件管理", desc = "'上传并安装插件'", saveData = false)
    public Result upload(@RequestParam("jarFile")MultipartFile multipartFile){
        try {
            UploadParam uploadParam = UploadParam.byMultipartFile(multipartFile)
                    .setBackOldPlugin(false)
                    .setStartPlugin(false)
                    .setUnpackPlugin(false);
            PluginInfo pluginInfo = pluginOperator.uploadPlugin(uploadParam);
            if (pluginInfo != null){
                SysPlugin plugin = new SysPlugin();
                plugin.setPluginId(pluginInfo.getPluginId());
                plugin.setIsInstall(Constants.YES);
                plugin.setPluginPath(pluginInfo.getPluginPath());
                plugin.setName(multipartFile.getOriginalFilename());
                plugin.setVersion(pluginInfo.getPluginDescriptor().getPluginVersion());
                plugin.setDescription(pluginInfo.getPluginDescriptor().getDescription());
                plugin.setStatus(Constants.NORMAL);
                if (!pluginService.save(plugin)){
                    pluginOperator.uninstall(pluginInfo.getPluginId(),true,true);
                    return Result.error("插件数据保存失败!");
                }
                return Result.success("插件安装成功、启动成功-插件ID: " + pluginInfo.getPluginId());
            }else {
                return Result.error("插件安装失败-插件ID: " + pluginInfo.getPluginId());
            }
        }catch (Exception e){
            return Result.error("插件安装异常-异常信息: " + e.getMessage());
        }
    }

    /**
     * 更新插件状态
     * @param plugin
     * @return
     */
    @PostMapping("/changeStatus")
    @Log(operType = "插件管理", desc = "'启动或关闭ID为[' + #plugin + ']的插件'")
    public Result changeStatus(@RequestBody SysPlugin plugin){
        try {
            if (Constants.NORMAL.equals(plugin.getStatus())){
                pluginOperator.start(plugin.getPluginId());
                return Result.success("插件" + plugin.getPluginId() + "启动成功");
            }else{
                pluginOperator.stop(plugin.getPluginId());
                return Result.success("插件" + plugin.getPluginId() + "停止成功");
            }

        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }


}
