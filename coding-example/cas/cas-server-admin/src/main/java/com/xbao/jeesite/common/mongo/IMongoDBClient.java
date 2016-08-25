package com.thinkgem.jeesite.common.mongo;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface IMongoDBClient {

    /**
     * 适合保存大文件, 如果保证文件名唯一，此方法比saveHugeFile快，如果文件存在就写入失败
     * 如果保存的文件是使用nginx插件读取的话，使用此方法
     * 
     * @param bucket 文件系统的文件表名称
     * @param filename 文件名，必填
     * @param contentType 文件类型，可空
     * @param bytes 文件内容
     * @throws MongoDBException
     */
    public void insertHugeFile(String bucket, String filename, String contentType, byte[] bytes)
            throws MongoDBException;

    /**
     * 适合保存大文件, 此方法需要先查找删除此文件名的文件后再保存 如果保存的文件是使用nginx插件读取的话，使用此方法
     * 
     * @param bucket 文件系统的文件表名称
     * @param filename 文件名，必填
     * @param contentType 文件类型，可空
     * @param bytes 文件内容
     * @throws MongoDBException
     */
    public void saveHugeFile(String bucket, String filename, String contentType, byte[] bytes) throws MongoDBException;

    /**
     * 适合删除大文件 如果保存的文件是使用nginx插件读取的话，使用此方法
     * 
     * @param bucket 文件系统的文件表名称
     * @param filename 文件名，必填
     * @throws MongoDBException
     */
    public void deleteHugeFile(String bucket, String filename) throws MongoDBException;

    /**
     * 适合获取大文件 如果保存的文件是使用nginx插件读取的话，使用此方法
     * 
     * @param bucket 文件系统的文件表名称
     * @param filename 文件名，必填
     * @return 文件流
     * @throws MongoDBException
     */
    public InputStream getHugeFile(String bucket, String filename) throws MongoDBException;

    /**
     * 适合获取大文件的唯一id 如果保存的文件是使用nginx插件读取的话，使用此方法
     * 
     * @param bucket 文件系统的文件表名称
     * @param filename 文件名，必填
     * @return _id mongodb自己生成的唯一id
     * @throws MongoDBException
     */
    public String getHugeFileID(String bucket, String filename) throws MongoDBException;

    /**
     * 适合保存大文件
     * 
     * @param bucket 文件系统的文件表名称
     * @String uniqueId 唯一值，必填
     * @String filename 文件名，可空
     * @param contentType 文件类型，可空
     * @param bytes 文件内容
     * @throws MongoDBException
     */
    public void saveHugeFile(String bucket, Object uniqueId, String filename, String contentType, byte[] bytes)
            throws MongoDBException;

    /**
     * 适合删除大文件
     * 
     * @param bucket 文件系统的文件表名称
     * @param uniqueId 唯一值，必填
     * @throws MongoDBException
     */
    public void deleteHugeFile(String bucket, Object uniqueId) throws MongoDBException;

    /**
     * 适合获取大文件
     * 
     * @param bucket 文件系统的文件表名称
     * @param uniqueId 唯一值，必填
     * @return 文件流
     * @throws MongoDBException
     */
    public InputStream getHugeFile(String bucket, Object uniqueId) throws MongoDBException;

    /**
     * 插入数据 效率高，不等待返回结果，不确定插入操作是否成功
     * 
     * @param tablename 类似表名
     * @param map
     * @throws MongoDBException
     */
    public void insert(String tablename, Map<String, Object> map) throws MongoDBException;

    /**
     * 批量插入数据 效率高，不等待返回结果，不确定插入操作是否成功
     * 
     * @param list
     * @param tablename 类似表名
     * @throws MongoDBException
     */
    public void insert(String tablename, List<Map<String, Object>> list) throws MongoDBException;

    /**
     * 插入数据 效率没insert高，等待返回结果 确定插入操作是否成功
     * 
     * @param map
     * @param tablename 类似表名
     * @return true:成功 | false:失败
     * @throws MongoDBException
     */
    public boolean insert_(String tablename, Map<String, Object> map) throws MongoDBException;

    /**
     * 批量插入数据 效率没insert高，等待返回结果 确定插入操作是否成功
     * 
     * @param list
     * @param tablename 类似表名
     * @return true:成功 | false:失败
     * @throws MongoDBException
     */
    public boolean insert_(String tablename, List<Map<String, Object>> list) throws MongoDBException;

    /**
     * 全量修改数据 效率高，不等待返回结果，不确定修改操作是否成功
     * 
     * @param tablename 类似表名
     * @param condition 查询条件
     * @param target 保存的数据
     * @throws MongoDBException
     */
    public void update(String tablename, Map<String, Object> condition, Map<String, Object> target)
            throws MongoDBException;

    /**
     * 全量修改数据 效率没update高，等待返回结果，确定修改操作是否成功
     * 
     * @param tablename 类似表名
     * @param condition 查询条件
     * @param target 保存的数据
     * @return true:成功 | false:失败
     * @throws MongoDBException
     */
    public boolean update_(String tablename, Map<String, Object> condition, Map<String, Object> target)
            throws MongoDBException;

    /**
     * 增量修改数据 效率高，不等待返回结果，不确定修改操作是否成功
     * 
     * @param tablename 类似表名
     * @param condition 查询条件
     * @param target 保存的数据
     * @throws MongoDBException
     */
    public void deltaUpdate(String tablename, Map<String, Object> condition, Map<String, Object> target)
            throws MongoDBException;

    /**
     * 增量修改数据 效率没deltaUpdate高，等待返回结果，确定修改操作是否成功
     * 
     * @param tablename 类似表名
     * @param condition 查询条件
     * @param target 保存的数据
     * @return true:成功 | false:失败
     * @throws MongoDBException
     */
    public boolean deltaUpdate_(String tablename, Map<String, Object> condition, Map<String, Object> target)
            throws MongoDBException;

    /**
     * 修改数据,如果不存在插入数据 效率高，不等待返回结果，不确定修改操作是否成功
     * 
     * @param tablename 类似表名
     * @param condition 查询条件
     * @param target 保存的数据
     * @throws MongoDBException
     */
    public void insertOrUpdate(String tablename, Map<String, Object> condition, Map<String, Object> target)
            throws MongoDBException;

    /**
     * 修改数据,如果不存在插入数据 效率没{@link #insertOrUpdate(String, Map, Map)}
     * 高，等待返回结果，确定修改操作是否成功
     * 
     * @param tablename 类似表名
     * @param condition 查询条件
     * @param target 保存的数据
     * @return true:成功 | false:失败
     * @throws MongoDBException
     */
    public boolean insertOrUpdate_(String tablename, Map<String, Object> condition, Map<String, Object> target)
            throws MongoDBException;

    /**
     * 删除数据 效率高，不等待返回结果，不确定删除操作是否成功
     * 
     * @param tablename 类似表名
     * @param condition 删除条件
     * @throws MongoDBException
     */
    public void delete(String tablename, Map<String, Object> condition) throws MongoDBException;

    /**
     * 删除数据 效率没delete高，等待返回结果，确定删除操作是否成功
     * 
     * @param tablename 类似表名
     * @param condition 删除条件
     * @return true:成功 | false:失败
     * @throws MongoDBException
     */
    public boolean delete_(String tablename, Map<String, Object> condition) throws MongoDBException;

    /**
     * 清空表
     * 
     * @param tablename
     * @return true:成功 | false:失败
     * @throws MongoDBException
     */
    public boolean tableClear(String tablename) throws MongoDBException;

    /**
     * 查询数据
     * 
     * @param tablename 类似表名
     * @param condition 查询条件
     * @param fields 返回的字段
     * @return
     * @throws MongoDBException
     */
    public Map<String, Object> selectOne(String tablename, Map<String, Object> condition, String... fields)
            throws MongoDBException;

    /**
     * 查询批量数据
     * 
     * @param tablename 类似表名
     * @param condition 查询条件
     * @param fields 返回的字段
     * @return
     * @throws MongoDBException
     */
    public List<Map<String, Object>> selectList(String tablename, Map<String, Object> condition, String... fields)
            throws MongoDBException;

    /**
     * 查询批量数据
     * 
     * @param tablename 类似表名
     * @param condition 查询条件
     * @param sort 排序条件(升序1,降序-1)
     * @param start 启始值
     * @param size 获取个数
     * @param String... fields 返回的字段
     * @return
     * @throws MongoDBException
     */
    public List<Map<String, Object>> selectList(String tablename, Map<String, Object> condition,
            Map<String, Object> sort, int start, int size, String... fields) throws MongoDBException;

    /**
     * 查询批量数据
     * 
     * @param tablename 类似表名
     * @param query 查询条件
     * @param fields 返回的字段
     * @return
     * @throws MongoDBException
     */
    public List<Map<String, Object>> selectList2(String tablename, IQuery query, String... fields)
            throws MongoDBException;

    /**
     * 查询批量数据
     * 
     * @param tablename 类似表名
     * @param query 查询条件
     * @param sort 排序条件(升序1,降序-1)
     * @param start 启始值
     * @param size 获取个数
     * @param String... fields 返回的字段
     * @return
     * @throws MongoDBException
     */
    public List<Map<String, Object>> selectList2(String tablename, IQuery query, Map<String, Object> sort, int start,
            int size, String... fields) throws MongoDBException;

    /**
     * 查询批量数据
     * 
     * @param tablename 类似表名
     * @param query 查询条件
     * @param sort 排序条件(升序1,降序-1)
     * @param limit 获取个数
     * @param fields 返回的字段
     * @return
     * @throws MongoDBException
     */
    public List<Map<String, Object>> selectList2(String tablename, IQuery query, Map<String, Object> sort, int limit,
            String... fields) throws MongoDBException;

    /**
     * Distinct 查询，返回全部数据
     * 
     * @param tablename 类似表名
     * @param field 唯一字段名
     * @return
     * @throws MongoDBException
     */
    public List<String> selectDistinctList(String tablename, String field) throws MongoDBException;

    /**
     * Distinct 查询，返回全部数据
     * 
     * @param tablename 类似表名
     * @param field 唯一字段名
     * @param condition 查询条件
     * @return
     * @throws MongoDBException
     */
    public List<String> selectDistinctList(String tablename, String field, Map<String, Object> condition)
            throws MongoDBException;

    /**
     * 查询总数
     * 
     * @param tablename 类似表名
     * @param condition 查询条件
     * @return
     * @throws MongoDBException
     */
    public long selectCount(String tablename, Map<String, Object> condition) throws MongoDBException;

}

