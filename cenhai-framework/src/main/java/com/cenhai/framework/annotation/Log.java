package com.cenhai.framework.annotation;

import java.lang.annotation.*;

@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

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
