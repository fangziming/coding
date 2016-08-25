package com.thinkgem.jeesite.common.mongo;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

public class MongoDBClientBean implements FactoryBean<IMongoDBClient>, DisposableBean {

    private String uri;
    private boolean slaveRead = false;

    private IMongoDBClient mongoDBClient;

    private static ConcurrentMap<String, Set<String>> register = new ConcurrentHashMap<String, Set<String>>();

    @Override
    public void destroy() throws Exception {
        String key = MongoDBClientFactory.toKey(uri);
        Set<String> alives = register.get(key);
        if (alives != null) {
            if (alives.remove(uri)) {
                if (alives.isEmpty()) {
                    MongoDBClientFactory.shutdown(uri);
                }
            }
        }
    }

    @Override
    public IMongoDBClient getObject() throws Exception {
        if (this.mongoDBClient == null) {
            this.mongoDBClient = MongoDBClientFactory.newInstance(uri, slaveRead);
            String key = MongoDBClientFactory.toKey(uri);
            Set<String> uris = register.get(key);
            if (uris == null) {
                uris = new HashSet<String>();
                register.put(key, uris);
            }
            uris.add(uri);
        }
        return this.mongoDBClient;
    }

    @Override
    public Class<?> getObjectType() {
        return IMongoDBClient.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public boolean isSlaveRead() {
        return slaveRead;
    }

    public void setSlaveRead(boolean slaveRead) {
        this.slaveRead = slaveRead;
    }

}

