package com.cenhai.common.utils;

import java.io.*;
import java.util.Base64;
import java.util.UUID;

/**
 * 图片工具
 * @author cenjihai
 */
public class ImageUtils {

    /**
     * 将base64 字符转为本地图片
     * @param base64Code
     * @param savePath
     * @return
     */
    public static String convertBase64ToImage(String base64Code, String savePath){
        String filename = UUID.randomUUID() + ".jpg";
        try {
            base64Code = base64Code.substring(base64Code.indexOf(",",1) + 1);
            byte[] bytes = Base64.getDecoder().decode(base64Code);
            for (int i = 0; i < bytes.length; i++){
                if (bytes[i] < 0){
                    bytes[i] += 256;
                }
            }
            OutputStream outputStream = new FileOutputStream(savePath + "/" + filename);
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
            return filename;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
