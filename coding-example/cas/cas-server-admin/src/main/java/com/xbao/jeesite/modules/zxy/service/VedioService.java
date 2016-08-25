/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zxy.service;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.zxy.entity.Chapter;
import com.thinkgem.jeesite.modules.zxy.entity.Vedio;
import com.thinkgem.jeesite.modules.zxy.utils.HttpTookit;
import com.thinkgem.jeesite.modules.zxy.dao.VedioDao;

/**
 * 视频管理Service
 * @author sheungxin
 * @version 2016-06-06
 */
@Service
@Transactional(readOnly = true)
public class VedioService extends CrudService<VedioDao, Vedio> {
	
	private static final String READTOKEN = "zQzcNPcnEX-hZ5o3BEQzU-SCE6ZK7YE0";
	@Autowired
	private ChapterService chapterService;
	
	public Vedio get(String id) {
		
		Vedio video = new Vedio();
		
		video.setId(id);
		
		List<Vedio> list =  findList(video);
		
		if(list!=null &&list.size()>0)
		return list.get(0);
		return null;
	}
	
	public List<Vedio> findList(Vedio vedio) {
		return super.findList(vedio);
	}
	
	public Page<Vedio> findPage(Page<Vedio> page, Vedio vedio) {
		return super.findPage(page, vedio);
	}
	
	@Transactional(readOnly = false)
	public void save(Vedio vedio) {
		super.save(vedio);
	}
	
	@Transactional(readOnly = false)
	public void delete(Vedio vedio) {
		super.delete(vedio);
	}
	
	@Transactional(readOnly = false)
	public int deleteAndCheck(Vedio vedio) {
		int flag=0;
		List<Chapter> chapterList=chapterService.getByObjId("0", vedio.getId());
		if(chapterList!=null&&chapterList.size()>0){
			flag=1;
		}else{
			delete(vedio);
		}
		return flag;
	}
	
	@Transactional(readOnly = false)
	public int saveAndCheck(Vedio vedio) {
		int flag=0;
		if(super.dao.getByUrl(vedio)!=null){
			flag=1;
		}else{
			save(vedio);
		}
		return flag;
	}
	
	@Transactional(readOnly = false)
	public int deleteAllAndCheck(Vedio vedio) {
		int flag=0;
		List<Chapter> chapterList=chapterService.getByObjId("0", vedio.getId());
		if(chapterList!=null&&chapterList.size()>0){
			flag=1;
		}else{
			delete(vedio);
		}
		return flag;
	}
	
	public void videoSync() {
		
		String videoInfoList = Global.getConfig("poly_url_videolist");
		
		if(StringUtils.isEmpty(videoInfoList)){
			logger.warn(" poly poly_url_videolist is null ！");
			return ;
		}
		//获取视频列表
		String result = HttpTookit.doGet(videoInfoList+"&readtoken="+READTOKEN, null, "UTF-8", false);
		//封装视频信息
		
		JSONObject jb = JSONObject.fromObject(result);
		
		if("0".equals(jb.getString("error"))){
		
			JSONArray ja = jb.getJSONArray("data");
			
//			List<Vedio> videoList = new ArrayList<Vedio>();
			
//			//删除库表中记录
//			super.deleteAll();
			
			//查询数据库中视频数据
			Vedio queryVedio = new Vedio();
			queryVedio.setDelFlag("0");
			List<Vedio> videoList = super.dao.findAllList(queryVedio);
			
			for(int i=0;i<ja.size();i++){
				
				Vedio video = new Vedio();
				JSONObject jbo = ja.getJSONObject(i);
				
				boolean flag =true;
				
				for(Vedio obj:videoList){
					if(obj.getUrl().equals(jbo.getString("vid"))){
						flag = false;
						break;
					}
				}
				
				if(flag){
					video.setUrl(jbo.getString("vid"));
					video.setName(jbo.getString("title"));
					video.setDuration(jbo.getString("duration"));
					video.setStatus(jbo.getString("status"));
					video.setTag(jbo.getString("tag"));
					
					User user = new User("admin", "admin");//默认用户
					
					video.setCreateBy(user);
					
					String cataId = jbo.getString("cataid");
					
	//				String catalogName = getCatalogName(cataId); 
	//				
	//				//获取分类名称
	//				video.setCatalog(catalogName);
					
					//获取分类ID
					video.setCatalog(cataId);
					
					//从数据库中获取查询
					
					super.save(video);
				}
			}
		}
		//根据vid修改视频信息
	}
}