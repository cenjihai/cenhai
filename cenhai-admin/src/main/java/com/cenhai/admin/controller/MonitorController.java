package com.cenhai.admin.controller;

import com.cenhai.common.utils.PageUtils;
import com.cenhai.common.utils.page.TableDataInfo;
import com.cenhai.common.web.controller.BaseController;
import com.cenhai.common.web.domain.Result;
import com.cenhai.framework.annotation.Log;
import com.cenhai.system.domain.SysOperlog;
import com.cenhai.system.param.OperlogQueryParam;
import com.cenhai.system.service.SysOperlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping("/monitor")
public class MonitorController extends BaseController {


    @Autowired
    private SysOperlogService operlogService;


    /**
     * 查询
     * @param param
     * @return
     */
    @GetMapping("/operlog/list")
    public Result<TableDataInfo> listOperlog (OperlogQueryParam param){
        PageUtils.startPage();
        return Result.success(PageUtils.getDataTable(operlogService.listOperlog(param)));
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @PostMapping("/operlog/delete")
    @Log(title = "操作日志",info = "删除日志")
    public Result deleteOperlog(@RequestBody Collection<Long> ids){
        return Result.result(operlogService.removeByIds(ids));
    }

    /**
     * 详情
     * @param operId
     * @return
     */
    @GetMapping("/operlog/{operId}")
    public Result<SysOperlog> operlogDetails(@PathVariable Long operId){
        return Result.success(operlogService.getById(operId));
    }
}
