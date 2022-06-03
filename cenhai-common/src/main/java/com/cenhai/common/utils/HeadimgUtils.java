package com.cenhai.common.utils;

import com.cenhai.common.config.SystemConfig;
import com.cenhai.common.constant.Constants;
import com.cenhai.common.exception.DefaultException;

public class HeadimgUtils {

    /**
     * 将base64的图片转为本地jpg图片。
     * @param base64Str
     */
    public static String saveBase64ToLocalImage(String base64Str, String serverUrl){
        //搜索头像字符串，如果有data:image 开头则生成图片
        if (StringUtils.isNotEmpty(base64Str) && base64Str.matches("^data:image(.*)")){
            String filename = ImageUtils.convertBase64ToImage(base64Str, SystemConfig.getAvatarPath());
            if (filename == null){
                throw new DefaultException("头像解析失败，请重新上传");
            }
            return serverUrl + Constants.RESOURCE_PREFIX + "/avatar/" + filename;
        }
        return base64Str;
    }

}
