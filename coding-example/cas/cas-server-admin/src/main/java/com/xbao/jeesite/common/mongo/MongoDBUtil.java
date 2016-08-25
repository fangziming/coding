package com.thinkgem.jeesite.common.mongo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.log4j.Logger;
import org.apache.poi.util.IOUtils;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;

public class MongoDBUtil{
    private static final Logger logger = Logger.getLogger(MongoDBUtil.class);
    
    private static IMongoDBClient mongoDBClient = null;
    
    private static ReentrantReadWriteLock mongoDBClientLock = new ReentrantReadWriteLock();
    
    public static IMongoDBClient getMongoDBClient(){
    	if(mongoDBClient == null){
    		mongoDBClientLock.writeLock().lock();
            try{
                if(mongoDBClient == null){
                	mongoDBClient = MongoDBClientFactory.newInstance(Global.getMongodbUri(), false);
                }
            }catch(Exception e){
                logger.warn("初始化MongoDBClient异常", e);
            }finally{
            	mongoDBClientLock.writeLock().unlock();
            }
        }
        return mongoDBClient;
    }
    
    public static String insertHugeFile(String bucket,String fileNames) throws FileNotFoundException, MongoDBException, IOException{
    	 if(StringUtils.isNoneBlank(fileNames)){
    		 for(String fileName:fileNames.split("\\|")){
    			 if(StringUtils.isNoneBlank(fileName)&&fileName.contains(Global.USERFILES_BASE_URL)){
    				 File upload_file = new File(Global.getUserfilesBaseDir() + URLDecoder.decode(fileName,"UTF-8"));
        	         if(upload_file != null&&upload_file.isFile()){
        	        	 String ext = fileName.substring(fileName.lastIndexOf(".", fileName.length() + 1), fileName.length());
        	        	 String formatName=UUID.randomUUID() + ext;
        	        	 getMongoDBClient().insertHugeFile(bucket, formatName, ext, IOUtils.toByteArray(new FileInputStream(upload_file)));
        	        	 fileNames=fileNames.replace(fileName, formatName);
        	         }
    			 }
    		 }
    	 }
    	 return fileNames;
    }
}
