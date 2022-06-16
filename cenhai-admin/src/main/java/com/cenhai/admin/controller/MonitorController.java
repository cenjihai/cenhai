package com.cenhai.admin.controller;

import com.cenhai.common.utils.page.PageUtils;
import com.cenhai.common.utils.page.TableDataInfo;
import com.cenhai.common.web.controller.BaseController;
import com.cenhai.common.web.domain.Result;
import com.cenhai.framework.annotation.OperatedLog;
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
    public TableDataInfo listOperlog (OperlogQueryParam param){
        PageUtils.startPage();
        return PageUtils.getDataTable(operlogService.listOperlog(param));
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @PostMapping("/operlog/delete")
    @OperatedLog(title = "操作日志",info = "删除日志")
    public Result deleteOperlog(@RequestBody Collection<Long> ids){
        if (operlogService.removeByIds(ids))return Result.success("删除成功");
            return Result.error("删除失败");
    }

    /**
     * 详情
     * @param operId
     * @return
     */
    @GetMapping("/operlog/{operId}")
    public SysOperlog operlogDetails(@PathVariable Long operId){
        return operlogService.getById(operId);
    }
}
