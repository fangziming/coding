package com.thinkgem.jeesite.modules.zxy.utils;

import java.io.Serializable;

/**
 * 学员通关状态
 * 
 * @author zhouruixing
 * 
 */
public enum EnumUserLockStatus implements Serializable
{
    NO_PASS("0", "未通关"), PASS("1", "已通关");
    
    private String code;
    
    private String name;
    
    public String getCode()
    {
        return code;
    }
    
    public String getName()
    {
        return name;
    }
    
    private EnumUserLockStatus(String code, String name)
    {
        this.code = code;
        this.name = name;
    }
    
    public static EnumUserLockStatus getEnum(String code)
    {
        for(EnumUserLockStatus e : EnumUserLockStatus.values())
        {
            if(e.getCode().equals(code))
            {
                return e;
            }
        }
       return null;
    }
    
}
