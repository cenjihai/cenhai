package com.cenhai.admin.controller;

import com.cenhai.common.utils.page.PageUtils;
import com.cenhai.common.utils.page.TableDataInfo;
import com.cenhai.common.web.controller.BaseController;
import com.cenhai.common.web.domain.Result;
import com.cenhai.framework.annotation.Log;
import com.cenhai.system.domain.SysOperlog;
import com.cenhai.system.param.OperlogQueryParam;
import com.cenhai.system.service.SysOperlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



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
     * 清空日志
     * @return
     */
    @DeleteMapping("/operlog/clean")
    @Log(operType = "操作日志管理", desc = "'清空日志'")
    public Result cleanOperlog(){
        operlogService.clean();
        return Result.success("清空成功");
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
