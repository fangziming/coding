package com.thinkgem.jeesite.modules.zxy.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.thinkgem.jeesite.common.config.Global;

/**
 * 展示互动工具类
 * @author Administrator
 *
 */
public class GenseeUtils {
	
	/**
	 * 创建直播：提供名称、开始时间，口令自动生成
	 * @param name
	 * @param startDate
	 * @return
	 */
	public static Map<String, Object> createTrainingRoom(String name,long startDate){
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("loginName", Global.getConfig("gensee_login_name"));
        parameterMap.put("password", Global.getConfig("gensee_password"));
        parameterMap.put("subject", name + new Date().getTime());
        parameterMap.put("startDate", startDate);
        parameterMap.put("scene", 0);// 0:大讲堂，1：小班课，2：私教课，默认值：0。当值scene为2，clientJoin,必须为true,同时webJoin为false
        // parameterMap.put("uiVideo", 2);// 设置是否视频为主
        parameterMap.put("realtime", true);// 无延迟模式
        parameterMap.put("uiMode", 4);
        // parameterMap.put("maxAttendees", 100);// 课堂最大并发数。 只有站点开启指定并发数功能，才能够设置。否则即使传入数据也无效。
        //parameterMap.put("sec", true);// 密码加密
        return HttpPostClient.post(Global.getConfig("gensee_classroom_create_url"), parameterMap);
	}

	/**
	 * 修改直播信息：目前仅限名称、开始时间
	 * @param name
	 * @param startDate
	 * @return
	 */
	public static Map<String, Object> modifyTrainingRoom(String sdkId,String name,long startDate){
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("loginName", Global.getConfig("gensee_login_name"));
        parameterMap.put("password", Global.getConfig("gensee_password"));
        parameterMap.put("subject", name+ new Date().getTime());
        parameterMap.put("startDate", startDate);
        parameterMap.put("realtime", true);// 无延迟模式
        parameterMap.put("id", sdkId);// 课堂ID
        return HttpPostClient.post(Global.getConfig("gensee_classroom_modify_url"), parameterMap);
	}
	
	/**
	 * 删除直播课堂
	 * @param sdkId
	 * @return
	 */
	public static Map<String, Object> deleteTrainingRoom(String sdkId){
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("loginName", Global.getConfig("gensee_login_name"));
        parameterMap.put("password", Global.getConfig("gensee_password"));
        parameterMap.put("id", sdkId);// 课堂ID
        return HttpPostClient.post(Global.getConfig("gensee_classroom_delete_url"), parameterMap);
	}
}
