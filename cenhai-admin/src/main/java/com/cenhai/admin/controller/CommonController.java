package com.cenhai.admin.controller;

import com.cenhai.common.config.SystemConfig;
import com.cenhai.common.web.controller.BaseController;
import com.cenhai.common.web.domain.Result;
import com.cenhai.framework.config.ServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/common")
public class CommonController extends BaseController {

    @Autowired
    private ServerConfig serverConfig;

    //上传文件
    @PostMapping("/upload")
    public Result upload(@RequestParam(value = "file") MultipartFile file){
        if (file.isEmpty())return Result.error("文件为空!");
        String originalFilename = file.getOriginalFilename();
        //获取后缀
        String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));

        String filePath = SystemConfig.getUploadPath() + "/";
        String newFilename = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + newFilename);
        if (!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest);
        }catch (IOException e){
            e.printStackTrace();
            return Result.error("上传失败");
        }
        Map<String,Object> result = new HashMap<>();
        result.put("url",serverConfig.getUrl());
        result.put("newFileName", newFilename);
        result.put("originalFilename",originalFilename);
        return Result.success(result);
    }
}
