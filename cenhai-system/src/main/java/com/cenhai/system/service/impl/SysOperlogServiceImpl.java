package com.cenhai.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cenhai.system.domain.SysOperlog;
import com.cenhai.system.param.OperlogQueryParam;
import com.cenhai.system.service.SysOperlogService;
import com.cenhai.system.mapper.SysOperlogMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author cenjihai
* @description 针对表【sys_operlog(系统操作日志)】的数据库操作Service实现
* @createDate 2022-05-30 16:33:45
*/
@Service
public class SysOperlogServiceImpl extends ServiceImpl<SysOperlogMapper, SysOperlog>
    implements SysOperlogService{

    /**
     * 查询
     * @param param
     * @return
     */
    @Override
    public List<SysOperlog> listOperlog(OperlogQueryParam param) {
        return baseMapper.listOperlog(param);
    }
}




