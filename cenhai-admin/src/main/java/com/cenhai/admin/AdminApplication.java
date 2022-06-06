package com.cenhai.admin;

import com.gitee.starblues.loader.launcher.SpringBootstrap;
import com.gitee.starblues.loader.launcher.SpringMainBootstrap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.cenhai"})
public class AdminApplication implements SpringBootstrap {
    public static void main(String[] args) {
        SpringMainBootstrap.launch(AdminApplication.class, args);
    }

    @Override
    public void run(String[] strings) throws Exception {
        SpringApplication.run(AdminApplication.class);
    }

}
