package com.cenhai.framework.annotation;

import java.lang.annotation.*;

/**
 * 操作日志
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 日志标题
     * @return
     */
    public String operType() default "";

    /**
     * 日志说明
     * 支持spel表达式
     * @return
     */
    public String desc() default "";

    /**
     * 是否保存请求响应数据
     * @return
     */
    public boolean saveData() default true;
}
