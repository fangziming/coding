package com.thinkgem.jeesite.modules.zxy.utils;

import java.io.Serializable;

/**
 * 用户类型普通/企业用户
 * 
 * @author juzx
 * 
 */
public enum EnumUserKind implements Serializable
{
    C("0", "个人"), E("1", "企业"), U("2", "院校");
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
    
    private EnumUserKind(String code, String name)
    {
        this.code = code;
        this.name = name;
    }
    
    public static EnumUserKind getEnum(String code)
    {
        for(EnumUserKind e : EnumUserKind.values())
        {
            if(e.getCode().equals(code))
            {
                return e;
            }
        }
       return null;
    }
}
