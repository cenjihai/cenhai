package com.cenhai.admin;

import com.gitee.starblues.loader.launcher.SpringBootstrap;
import com.gitee.starblues.loader.launcher.SpringMainBootstrap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

@SpringBootApplication
@ComponentScan(basePackages = {"com.cenhai"})
public class AdminApplication implements SpringBootstrap {
    public static void main(String[] args) {
        disableWarning();
        SpringMainBootstrap.launch(AdminApplication.class, args);
    }

    @Override
    public void run(String[] strings) throws Exception {
        SpringApplication.run(AdminApplication.class);
    }

    /**
     * 禁用警告。毫无作用只是不显示
     */
    public static void disableWarning() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            Unsafe u = (Unsafe) theUnsafe.get(null);

            Class cls = Class.forName("jdk.internal.module.IllegalAccessLogger");
            Field logger = cls.getDeclaredField("logger");
            u.putObjectVolatile(cls, u.staticFieldOffset(logger), null);
        } catch (Exception e) {
            // ignore
        }
    }

}
