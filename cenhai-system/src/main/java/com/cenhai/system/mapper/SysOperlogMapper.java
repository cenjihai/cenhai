package com.cenhai.system.mapper;

import com.cenhai.system.domain.SysOperlog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cenhai.system.param.OperlogQueryParam;

import java.util.List;

/**
* @author cenjihai
* @description 针对表【sys_operlog(系统操作日志)】的数据库操作Mapper
* @createDate 2022-05-30 16:33:45
* @Entity com.cenhai.system.domain.SysOperlog
*/
public interface SysOperlogMapper extends BaseMapper<SysOperlog> {

    List<SysOperlog> listOperlog(OperlogQueryParam param);
}




