package com.thinkgem.jeesite.common.mongo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

public class MongoDBClientImpl implements IMongoDBClient {

    private Mongo mongo;

    private String database;

    public MongoDBClientImpl(Mongo mongo, String database) {
        this.mongo = mongo;
        this.database = database;
    }

    private DB getDB() {
        return mongo.getDB(database);
    }

    private GridFS getGridFS(String bucket) {
        return new GridFS(getDB(), bucket);
    }

    @Override
    public void insertHugeFile(String bucket, String filename, String contentType, byte[] bytes)
            throws MongoDBException {
        if (bucket == null || filename == null || bytes == null || bytes.length == 0) {
            throw new MongoDBException("saveHugeFile method required params is null");
        }

        GridFS gridFS = getGridFS(bucket);
        GridFSInputFile gridFSInputFile = gridFS.createFile(bytes);
        gridFSInputFile.put("filename", filename);
        if (contentType != null) {
            gridFSInputFile.put("contentType", contentType);
        }
        gridFSInputFile.save();
    }

    @Override
    public void saveHugeFile(String bucket, String filename, String contentType, byte[] bytes) throws MongoDBException {
        if (bucket == null || filename == null || bytes == null || bytes.length == 0) {
            throw new MongoDBException("saveHugeFile method required params is null");
        }

        GridFS gridFS = getGridFS(bucket);
        // 相同的filename 必须先删除才能再保存
        gridFS.remove(filename);
        GridFSInputFile gridFSInputFile = gridFS.createFile(bytes);
        gridFSInputFile.put("filename", filename);
        if (contentType != null) {
            gridFSInputFile.put("contentType", contentType);
        }
        gridFSInputFile.save();
    }

    @Override
    public void deleteHugeFile(String bucket, String filename) throws MongoDBException {
        if (bucket == null || filename == null) {
            throw new MongoDBException("deleteHugeFile method required params is null");
        }
        GridFS gridFS = getGridFS(bucket);
        gridFS.remove(filename);
    }

    @Override
    public InputStream getHugeFile(String bucket, String filename) throws MongoDBException {
        if (bucket == null || filename == null) {
            throw new MongoDBException("getHugeFile method required params is null");
        }
        GridFS gridFS = getGridFS(bucket);
        GridFSDBFile gridFSDBFile = gridFS.findOne(filename);
        return (gridFSDBFile != null) ? gridFSDBFile.getInputStream() : null;
    }

    @Override
    public String getHugeFileID(String bucket, String filename) throws MongoDBException {
        if (bucket == null || filename == null) {
            throw new MongoDBException("getHugeFile method required params is null");
        }
        GridFS gridFS = getGridFS(bucket);
        GridFSDBFile gridFSDBFile = gridFS.findOne(filename);
        return (gridFSDBFile != null) ? gridFSDBFile.getId().toString() : null;
    }

    @Override
    public void saveHugeFile(String bucket, Object uniqueId, String filename, String contentType, byte[] bytes)
            throws MongoDBException {
        if (bucket == null || uniqueId == null || bytes == null || bytes.length == 0) {
            throw new MongoDBException("saveHugeFile method required params is null");
        }

        GridFS gridFS = getGridFS(bucket);
        GridFSInputFile gridFSInputFile = gridFS.createFile(bytes);
        gridFSInputFile.put("_id", uniqueId);
        gridFSInputFile.put("filename", filename);
        if (contentType != null) {
            gridFSInputFile.put("contentType", contentType);
        }
        gridFSInputFile.save();
    }

    @Override
    public void deleteHugeFile(String bucket, Object uniqueId) throws MongoDBException {
        if (bucket == null || uniqueId == null) {
            throw new MongoDBException("deleteHugeFile method required params is null");
        }
        GridFS gridFS = getGridFS(bucket);
        gridFS.remove(new BasicDBObject("_id", uniqueId));
    }

    @Override
    public InputStream getHugeFile(String bucket, Object uniqueId) throws MongoDBException {
        if (bucket == null || uniqueId == null) {
            throw new MongoDBException("getHugeFile method required params is null");
        }
        GridFS gridFS = getGridFS(bucket);
        GridFSDBFile gridFSDBFile = gridFS.findOne(new BasicDBObject("_id", uniqueId));
        return (gridFSDBFile != null) ? gridFSDBFile.getInputStream() : null;
    }

    @Override
    public void insert(String tablename, Map<String, Object> map) throws MongoDBException {
        if (tablename == null || map == null || map.isEmpty()) {
            throw new MongoDBException("insert method required params is null");
        }
        DBCollection dbCollection = getDB().getCollection(tablename);
        BasicDBObject dbObject = new BasicDBObject(map);
        dbCollection.insert(dbObject);
    }

    @Override
    public void insert(String tablename, List<Map<String, Object>> list) throws MongoDBException {
        if (tablename == null || list == null || list.isEmpty()) {
            throw new MongoDBException("insert method required params is null");
        }
        List<DBObject> dBObjects = change(list);
        if (dBObjects.isEmpty()) {
            return;
        }
        DBCollection dbCollection = getDB().getCollection(tablename);
        dbCollection.insert(dBObjects);
    }

    @SuppressWarnings("deprecation")
	@Override
    public boolean insert_(String tablename, Map<String, Object> map) throws MongoDBException {
        if (tablename == null || map == null || map.isEmpty()) {
            throw new MongoDBException("insert_ method required params is null");
        }
        BasicDBObject dbObject = new BasicDBObject(map);
        DBCollection dbCollection = getDB().getCollection(tablename);
        WriteResult writeResult = dbCollection.insert(dbObject, WriteConcern.SAFE);
        if (writeResult != null) {
            return writeResult.getLastError().ok() == true;
        }
        return false;
    }

    @SuppressWarnings("deprecation")
	@Override
    public boolean insert_(String tablename, List<Map<String, Object>> list) throws MongoDBException {
        if (tablename == null || list == null || list.isEmpty()) {
            throw new MongoDBException("insert_ method required params is null");
        }
        List<DBObject> dBObjects = change(list);
        if (dBObjects.isEmpty()) {
            throw new MongoDBException("insert_ method required params is empty");
        }
        DBCollection dbCollection = getDB().getCollection(tablename);
        WriteResult writeResult = dbCollection.insert(dBObjects, WriteConcern.SAFE);
        if (writeResult != null) {
            return writeResult.getLastError().ok() == true;
        }
        return false;
    }

    @Override
    public void update(String tablename, Map<String, Object> condition, Map<String, Object> target)
            throws MongoDBException {
        if (tablename == null || condition == null || condition.isEmpty() || target == null || target.isEmpty()) {
            throw new MongoDBException("update method required params is null");
        }
        DBObject q = new BasicDBObject(condition);
        DBObject o = new BasicDBObject(target);
        getDB().getCollection(tablename).update(q, o);
    }

    @SuppressWarnings("deprecation")
	@Override
    public boolean update_(String tablename, Map<String, Object> condition, Map<String, Object> target)
            throws MongoDBException {
        if (tablename == null || condition == null || condition.isEmpty() || target == null || target.isEmpty()) {
            throw new MongoDBException("update_ method required params is null");
        }
        DBObject q = new BasicDBObject(condition);
        DBObject o = new BasicDBObject(target);
        WriteResult writeResult = getDB().getCollection(tablename).update(q, o, false, false, WriteConcern.SAFE);
        if (writeResult != null) {
            return writeResult.getLastError().ok() == true;
        }
        return false;
    }

    @Override
    public void deltaUpdate(String tablename, Map<String, Object> condition, Map<String, Object> target)
            throws MongoDBException {
        if (tablename == null || condition == null || condition.isEmpty() || target == null || target.isEmpty()) {
            throw new MongoDBException("deltaUpdate method required params is null");
        }
        DBObject q = new BasicDBObject(condition);
        BasicDBObject o = new BasicDBObject("$set", new BasicDBObject(target));
        getDB().getCollection(tablename).update(q, o);
    }

    @SuppressWarnings("deprecation")
	@Override
    public boolean deltaUpdate_(String tablename, Map<String, Object> condition, Map<String, Object> target)
            throws MongoDBException {
        if (tablename == null || condition == null || condition.isEmpty() || target == null || target.isEmpty()) {
            throw new MongoDBException("deltaUpdate_ method required params is null");
        }
        DBObject q = new BasicDBObject(condition);
        BasicDBObject o = new BasicDBObject("$set", new BasicDBObject(target));
        WriteResult writeResult = getDB().getCollection(tablename).update(q, o, false, false, WriteConcern.SAFE);
        if (writeResult != null) {
            return writeResult.getLastError().ok() == true;
        }
        return false;
    }

    @Override
    public void insertOrUpdate(String tablename, Map<String, Object> condition, Map<String, Object> target)
            throws MongoDBException {
        if (tablename == null || condition == null || condition.isEmpty() || target == null || target.isEmpty()) {
            throw new MongoDBException("update method required params is null");
        }
        DBObject q = new BasicDBObject(condition);
        DBObject o = new BasicDBObject(target);
        getDB().getCollection(tablename).update(q, o, true, false);
    }

    @SuppressWarnings("deprecation")
	@Override
    public boolean insertOrUpdate_(String tablename, Map<String, Object> condition, Map<String, Object> target)
            throws MongoDBException {
        if (tablename == null || condition == null || condition.isEmpty() || target == null || target.isEmpty()) {
            throw new MongoDBException("update method required params is null");
        }
        DBObject q = new BasicDBObject(condition);
        DBObject o = new BasicDBObject(target);
        WriteResult writeResult = getDB().getCollection(tablename).update(q, o, true, false, WriteConcern.SAFE);
        if (writeResult != null) {
            return writeResult.getLastError().ok() == true;
        }
        return false;
    }

    @Override
    public void delete(String tablename, Map<String, Object> condition) throws MongoDBException {
        if (tablename == null || condition == null || condition.isEmpty()) {
            throw new MongoDBException("delete method required params is null");
        }
        DBObject q = new BasicDBObject(condition);
        getDB().getCollection(tablename).remove(q);
    }

    @SuppressWarnings("deprecation")
	@Override
    public boolean delete_(String tablename, Map<String, Object> condition) throws MongoDBException {
        if (tablename == null || condition == null || condition.isEmpty()) {
            throw new MongoDBException("delete_ method required params is null");
        }
        DBObject q = new BasicDBObject(condition);
        WriteResult writeResult = getDB().getCollection(tablename).remove(q, WriteConcern.SAFE);
        if (writeResult != null) {
            return writeResult.getLastError().ok() == true;
        }
        return false;
    }

    @SuppressWarnings("deprecation")
	@Override
    public boolean tableClear(String tablename) throws MongoDBException {
        if (tablename == null) {
            throw new MongoDBException("tableClear method required params is null");
        }
        WriteResult writeResult = getDB().getCollection(tablename).remove(new BasicDBObject(), WriteConcern.SAFE);
        if (writeResult != null) {
            return writeResult.getLastError().ok() == true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> selectOne(String tablename, Map<String, Object> condition, String... fields)
            throws MongoDBException {
        if (tablename == null || condition == null || condition.isEmpty()) {
            throw new MongoDBException("selectOne method required params is null");
        }
        DBObject o = new BasicDBObject(condition);
        DBObject dbObject = null;
        if (fields == null || fields.length == 0) {
            dbObject = getDB().getCollection(tablename).findOne(o);
        } else {
            dbObject = getDB().getCollection(tablename).findOne(o, toField(fields));
        }
        if (dbObject != null) {
            return dbObject.toMap();
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> selectList(String tablename, Map<String, Object> condition, String... fields)
            throws MongoDBException {
        return selectList(tablename, condition, null, 0, Integer.MAX_VALUE, fields);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> selectList(String tablename, Map<String, Object> condition,
            Map<String, Object> sort, int start, int size, String... fields) throws MongoDBException {
        if (tablename == null) {
            throw new MongoDBException("selectList method required params is null");
        }
        DBObject o = (condition != null) ? new BasicDBObject(condition) : null;
        DBCursor dBCursor = null;
        if (fields == null || fields.length == 0) {
            dBCursor = getDB().getCollection(tablename).find(o);
        } else {
            dBCursor = getDB().getCollection(tablename).find(o, toField(fields));
        }
        if (sort != null) {
            DBObject s = new BasicDBObject(sort);
            dBCursor = dBCursor.sort(s);
        }

        dBCursor = dBCursor.skip(start).limit(size);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        while (dBCursor.hasNext()) {
            DBObject tmp = dBCursor.next();
            list.add(tmp.toMap());
        }
        dBCursor.close();
        return list;
    }

    @Override
    public List<Map<String, Object>> selectList2(String tablename, IQuery query, String... fields)
            throws MongoDBException {
        return selectList2(tablename, query, null, 0, Integer.MAX_VALUE, fields);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> selectList2(String tablename, IQuery query, Map<String, Object> sort, int start,
            int size, String... fields) throws MongoDBException {
        if (tablename == null || query == null) {
            throw new MongoDBException("selectList method required params is null");
        }
        DBObject o = query.build();
        DBCursor dBCursor = null;
        if (fields == null || fields.length == 0) {
            dBCursor = getDB().getCollection(tablename).find(o);
        } else {
            dBCursor = getDB().getCollection(tablename).find(o, toField(fields));
        }
        if (sort != null) {
            DBObject s = new BasicDBObject(sort);
            dBCursor = dBCursor.sort(s);
        }

        dBCursor = dBCursor.skip(start).limit(size);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        while (dBCursor.hasNext()) {
            DBObject tmp = dBCursor.next();
            list.add(tmp.toMap());
        }
        dBCursor.close();
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> selectList2(String tablename, IQuery query, Map<String, Object> sort, int limit,
            String... fields) throws MongoDBException {
        if (tablename == null || query == null) {
            throw new MongoDBException("selectList method required params is null");
        }
        DBObject o = query.build();
        DBCursor dBCursor = null;
        if (fields == null || fields.length == 0) {
            dBCursor = getDB().getCollection(tablename).find(o);
        } else {
            dBCursor = getDB().getCollection(tablename).find(o, toField(fields));
        }
        if (sort != null) {
            DBObject s = new BasicDBObject(sort);
            dBCursor = dBCursor.sort(s);
        }

        dBCursor = dBCursor.limit(limit);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        while (dBCursor.hasNext()) {
            DBObject tmp = dBCursor.next();
            list.add(tmp.toMap());
        }
        dBCursor.close();
        return list;
    }

    @Override
    public List<String> selectDistinctList(String tablename, String field) throws MongoDBException {
        return selectDistinctList(tablename, field, null);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<String> selectDistinctList(String tablename, String field, Map<String, Object> condition)
            throws MongoDBException {
        if (tablename == null || field == null) {
            throw new MongoDBException("selectDistinctList method required params is null");
        }
        List<String> list = null;
        if (condition != null) {
            BasicDBObject query = new BasicDBObject(condition);
            list = getDB().getCollection(tablename).distinct(field, query);
        } else {
            list = getDB().getCollection(tablename).distinct(field);
        }
        return list;
    }

    @Override
    public long selectCount(String tablename, Map<String, Object> condition) throws MongoDBException {
        if (tablename == null || condition == null || condition.isEmpty()) {
            throw new MongoDBException("selectList method required params is null");
        }
        DBObject o = new BasicDBObject(condition);
        return getDB().getCollection(tablename).count(o);
    }

    private DBObject toField(String... fields) {
        DBObject f = new BasicDBObject();
        for (String field : fields) {
            if (field != null) {
                f.put(field, 1);
            }
        }
        return f;
    }

    private List<DBObject> change(List<Map<String, Object>> list) {
        List<DBObject> dBObjects = new ArrayList<DBObject>();
        for (Map<String, Object> map : list) {
            if (map == null || map.isEmpty()) {
                continue;
            }
            dBObjects.add(new BasicDBObject(map));
        }
        return dBObjects;
    }

}
