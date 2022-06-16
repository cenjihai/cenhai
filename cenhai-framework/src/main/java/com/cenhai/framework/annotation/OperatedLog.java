package com.cenhai.framework.annotation;

import java.lang.annotation.*;

/**
 * 操作日志
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperatedLog {

    /**
     * 日志标题
     * @return
     */
    public String title() default "";

    /**
     * 日志说明
     * @return
     */
    public String info() default "";
}
