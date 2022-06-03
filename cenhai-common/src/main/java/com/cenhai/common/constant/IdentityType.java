package com.cenhai.common.constant;

import com.cenhai.common.exception.ServiceException;
import com.cenhai.common.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

public enum IdentityType {

    password("password");

    private String value;

    IdentityType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private static final Map<String,IdentityType> MAPPINGS = new HashMap<>(64);

    static {
        for (IdentityType anEnum : values()){
            MAPPINGS.put(anEnum.getValue(),anEnum);
        }
    }

    public static IdentityType resolve(String type){
        if (StringUtils.isNotEmpty(type) && MAPPINGS.containsKey(type) && MAPPINGS.get(type) != null){
            return MAPPINGS.get(type);
        }
        throw new ServiceException("身份类型不支持!");
    }
}
