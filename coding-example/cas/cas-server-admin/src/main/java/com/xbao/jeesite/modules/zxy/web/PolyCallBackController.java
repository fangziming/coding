/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.zxy.entity.Vedio;
import com.thinkgem.jeesite.modules.zxy.service.VedioService;
import com.thinkgem.jeesite.modules.zxy.utils.HttpTookit;


@Controller
@RequestMapping(value = "/zxy/poly")
public class PolyCallBackController extends BaseController {
	
	private static final String TYPE_UPLOAD = "upload";//已上传
//	private static final String TYPE_INVALID_VIDEO = "invalidVideo";//不合规格视频
	private static final String TYPE_ENCODE = "encode";//已编码
	private static final String TYPE_ENCODE_FAILED = "encode_failed";//处理失败
	private static final String TYPE_PASS = "pass";//通过
	private static final String TYPE_NOPASS = "nopass";//未通过
	private static final String TYPE_DEL = "del";//删除
	
	private static final String READTOKEN = "zQzcNPcnEX-hZ5o3BEQzU-SCE6ZK7YE0";
	
	private Logger logger = Logger.getLogger(PolyCallBackController.class);
	

	@Autowired
	private VedioService vedioService;
	
	@RequestMapping(value = "polyvcallback")
	public void polyvcallback(HttpServletRequest request,HttpServletResponse response) {
		
//		String sign = request.getParameter("sign");
		
		String type = request.getParameter("type");
		
		String vid = request.getParameter("vid");
		
		Vedio video = null;
		
		if(TYPE_UPLOAD.equals(type)){//已上传
			//根据vid获取视频信息
			String videoInfo = getVideoByPoly(vid);
			
			if(StringUtils.isEmpty(videoInfo)) return ;
			
			JSONObject jb = JSONObject.fromObject(videoInfo);
			
			if("0".equals(jb.getString("error"))){
				video = formatVideo( jb, vid);
			}else{
				return;
			}
			
		//}else if(TYPE_INVALID_VIDEO.equals(type)){//不合规格视频（上传失败不处理）
		}else {
			
			Vedio queryVideo = new Vedio();
			queryVideo.setUrl(vid);
			
			video = vedioService.get(queryVideo);
			
			if(video ==null){
				String videoInfo = getVideoByPoly(vid);
				
				JSONObject jb = JSONObject.fromObject(videoInfo);
				
				if("0".equals(jb.getString("error"))){
					video = formatVideo( jb, vid);
				}else{
					return;
				}
			}
			if(TYPE_ENCODE.equals(type)){//已编码
				//获取数据库中该视频，如果没有，通过保利威视获取视频信息入库
				video.setStatus("50");
			}else if(TYPE_ENCODE_FAILED.equals(type)){//处理失败
				video.setStatus("21");
			}else if(TYPE_PASS.equals(type)){//通过
				video.setStatus("60");
				
			}else if(TYPE_NOPASS.equals(type)){//未通过
				video.setStatus("51");
			}else if(TYPE_DEL.equals(type)){//删除
				video.setStatus("-1");
			}
		}
		
		video.setUpdateDate(new Date());
		int flag=vedioService.saveAndCheck(video);
		if(flag==0){
			if(logger.isInfoEnabled())
			logger.info("保存视频成功 video="+video);
			
		}else{
			if(logger.isInfoEnabled())
				logger.info("保存视频失败 video="+video);
		}
	}
	
	public String getVideoByPoly(String vid){
		
		String videoInfoUrl = Global.getConfig("poly_url_videoinfo");
		
		if(StringUtils.isEmpty(videoInfoUrl)){
			
			logger.warn(" poly_url_videoinfo is null ！");
			return null;
		}
		
		String result = HttpTookit.doGet(videoInfoUrl+"&readtoken="+READTOKEN+"&vid="+vid, null, "UTF-8", false);
		
		if(logger.isInfoEnabled()) logger.info(result);
		
		return result;
	}
	
	public String getCatalogName(String cataId){
		
		String catalogInfoUrl = Global.getConfig("poly_url_cataloginfo");
		
		if(StringUtils.isEmpty(catalogInfoUrl)){
			
			logger.warn(" poly_url_cataloginfo is null ！");
			return null;
		}
		
		String result = HttpTookit.doGet(catalogInfoUrl+"&readtoken="+READTOKEN+"&cataid="+cataId, null, "UTF-8", false);
		
		JSONObject jb = JSONObject.fromObject(result);
		
		if(jb!=null && "0".equals(jb.getString("error"))){
			JSONArray ja = jb.getJSONArray("data");
			
			for(int i=0;i<ja.size();i++){
				
				JSONObject jbo = ja.getJSONObject(i);
				if(cataId.equals(jbo.getString("cataid"))){
					return jbo.getString("cataname");
				}
			}
		}
		
		return null;
	}
	
	public Vedio formatVideo(JSONObject jb,String vid){
		Vedio video = new Vedio();
		
		JSONArray ja =  jb.getJSONArray("data");
		jb = ja.getJSONObject(0);
		
		video.setName(jb.getString("title"));
		video.setUrl(vid);
		video.setDuration(jb.getString("duration"));
		video.setStatus(jb.getString("status"));
		video.setTag(jb.getString("tag"));
		
		User user = new User("admin", "admin");//默认用户
		
		video.setCreateBy(user);
		
		String cataId = jb.getString("tag");
		
//		String catalogName = getCatalogName(cataId); 
//		
//		//获取分类名称
//		video.setCatalog(catalogName);
		
		//获取分类ID
		video.setCatalog(cataId);
		
		return video;
	}
	
}