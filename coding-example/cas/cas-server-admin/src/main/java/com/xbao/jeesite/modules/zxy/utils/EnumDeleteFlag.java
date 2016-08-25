package com.thinkgem.jeesite.modules.zxy.utils;

import java.io.Serializable;

/**
 * 数据逻辑删除状态
 * 
 * @author zhouruixing
 * 
 */
public enum EnumDeleteFlag implements Serializable
{
    // 0 未删除，默认 1 已删除
    NOT_DELETED("0", "未删除，默认"), DELETED("1", "已删除");
    
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
    
    private EnumDeleteFlag(String code, String name)
    {
        this.code = code;
        this.name = name;
    }
    
    public static EnumDeleteFlag getEnum(String code)
    {
        for(EnumDeleteFlag e : EnumDeleteFlag.values())
        {
            if(e.getCode().equals(code))
            {
                return e;
            }
        }
        
        return null;
    }
    
}
