package com.cenhai.system.service;

import com.cenhai.system.domain.SysOperlog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cenhai.system.param.OperlogQueryParam;

import java.util.List;

/**
* @author cenjihai
* @description 针对表【sys_operlog(系统操作日志)】的数据库操作Service
* @createDate 2022-05-30 16:33:45
*/
public interface SysOperlogService extends IService<SysOperlog> {

    /**
     * 查询
     * @param param
     * @return
     */
    List<SysOperlog> listOperlog(OperlogQueryParam param);

    /**
     * 清空日志
     * @return
     */
    boolean clean();
}
