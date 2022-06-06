package com.cenhai.framework.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.*;

@Configuration
@EnableTransactionManagement
@MapperScan("com.cenhai.**.mapper")
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * 自动填充数据库创建人、创建时间、更新时间
     */
    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setMetaObjectHandler(new AutoUpdateDateMetaObjectHandler());
        globalConfig.setBanner(false);
        return globalConfig;
    }

    @Component
    class AutoUpdateDateMetaObjectHandler implements MetaObjectHandler{

        /**
         * 新增填充创建时间
         *
         * @param metaObject
         */
        @Override
        public void insertFill(MetaObject metaObject) {
            this.setFieldValByName("createTime",new Date(),metaObject);
        }

        /**
         * 更新填充更新时间
         *
         * @param metaObject
         */
        @Override
        public void updateFill(MetaObject metaObject) {
           this.setFieldValByName("updateTime",new Date(),metaObject);
        }
    }
}
