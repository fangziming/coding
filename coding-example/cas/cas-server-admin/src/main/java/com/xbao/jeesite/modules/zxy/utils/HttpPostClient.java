package com.thinkgem.jeesite.modules.zxy.utils;

import java.util.Map;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.StringUtils;

public class HttpPostClient{
	
    @SuppressWarnings("unchecked")
	public static Map<String, Object> post(String url, Map<String, Object> parameterMap){
        Map<String, Object> resultMap = null;
        if(StringUtils.isNoneBlank(url)){
        	HttpClient httpclient = new HttpClient();
            PostMethod postMethod = new UTF8PostMethod(url);
            for(String key : parameterMap.keySet()){
                postMethod.addParameter(key, parameterMap.get(key).toString());
            }
            try{
                int statusCode = httpclient.executeMethod(postMethod);
                if(statusCode == 200){
                    String result = postMethod.getResponseBodyAsString();
                    result = new String(result.getBytes("ISO-8859-1"), "UTF-8");
                    resultMap = (Map<String, Object>)JsonMapper.fromJsonString(result, Map.class);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return resultMap;
    }
    
    public static class UTF8PostMethod extends PostMethod{
        public UTF8PostMethod(String url){
            super(url);
        }
        
        @Override
        public String getRequestCharSet(){
            return "UTF-8";
        }
    }
}
