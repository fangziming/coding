package com.thinkgem.jeesite.common.mongo;

import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoURI;
import com.mongodb.ReadPreference;

public final class MongoDBClientFactory {

    private static final ConcurrentMap<String, Mongo> _mongoInstances = new ConcurrentHashMap<String, Mongo>();

    public static final int MAX_POOL_SIZE = 32;

    public static final int WAIT_QUEUE_TIMEOUTMS = 5000;

    public static final int CONNECT_TIMEOUTMS = 2000;

    public static final int SOCKET_TIMEOUTMS = 10000;

    public static final boolean AUTO_CONNECT_RETRY = true;

    public static final boolean SLAVE_OK = false; // true：SECONDARY
                                                  // false：PRIMARY

    /**
     * 获取IMongoDBClient接口实现类, 线程安全
     * 
     * mongodb://[username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database][?options]]
     * 
     * examples
     * mongodb://username:password@127.0.0.1:10001/test?maxpoolsize=128;waitqueuetimeoutms
     * =5000;connecttimeoutms=2000;sockettimeoutms=10000;autoconnectretry=true
     * 
     * @param uri the URI
     * @throws UnknownHostException
     * @throws MongoException
     */
    public static final IMongoDBClient newInstance(String uri) throws MongoException, UnknownHostException {
        return newInstance(uri, SLAVE_OK);
    }

    @SuppressWarnings("deprecation")
	public static final IMongoDBClient newInstance(String uri, boolean slaveRead) throws MongoException,
            UnknownHostException {
        MongoURI mongoUri = new MongoURI(uri);
        Mongo mongo = Mongo.Holder.singleton().connect(mongoUri);
        mongo.getMongoOptions().socketKeepAlive = true;
        if (slaveRead) {
            mongo.setReadPreference(ReadPreference.secondary());
        }
        String key = toKey(mongoUri);

        String database = mongoUri.getDatabase();
        if (database == null) {
            throw new RuntimeException("unknow database for mongodb uri" + uri);
        }

        DB db = mongo.getDB(database);
        if (!db.isAuthenticated()) {
            if (!db.authenticate(mongoUri.getUsername(), mongoUri.getPassword())) {
                throw new RuntimeException("mongodb auth name/password fail" + uri);
            }
        }

        _mongoInstances.putIfAbsent(key, mongo);
        return new MongoDBClientImpl(mongo, database);
    }

    /**
     * 关闭指定打开的mongo客户端链接
     * 
     */
    public synchronized static final void shutdown(String uri) {
        String key = toKey(uri);
        Mongo mongo = _mongoInstances.get(key);
        if (mongo != null) {
            mongo.close();
        }
        mongo = null;
        _mongoInstances.remove(key);
    }

    /**
     * 关闭所有打开的mongo客户端链接
     * 
     */
    public synchronized static final void shutdownAll() {
        for (Mongo mongo : _mongoInstances.values()) {
            mongo.close();
        }
        _mongoInstances.clear();
    }

    public static String toKey(MongoURI uri) {
        StringBuilder buf = new StringBuilder();
        for (String h : uri.getHosts())
            buf.append(h).append(",");
        buf.append(uri.getOptions());
        buf.append(uri.getUsername());
        return buf.toString();
    }

    public static String toKey(String uri) {
        @SuppressWarnings("deprecation")
		MongoURI mongoUri = new MongoURI(uri);
        return toKey(mongoUri);
    }
}

