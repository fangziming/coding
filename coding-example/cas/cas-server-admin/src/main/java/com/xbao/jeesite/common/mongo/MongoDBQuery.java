package com.thinkgem.jeesite.common.mongo;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

public class MongoDBQuery implements IQuery {

    @Override
    public BasicDBObject build() {
        // TODO
        return null;
    }

    private interface KeyMark {
        public String name();
    }

    public static class OR implements KeyMark, IQuery {

        private BasicDBList where = new BasicDBList();

        public OR put(String key, Object val) {
            where.add(new BasicDBObject(key, val));
            return this;
        }

        @Override
        public String name() {
            return "$or";
        }

        @Override
        public BasicDBObject build() {
            return new BasicDBObject(name(), where);
        }
    }

    public static class AND implements IQuery {

        private BasicDBObject where = new BasicDBObject();

        public void put(String key, Object val) {
            where.put(key, val);
        }

        public AND add(IQuery query) {
            where.putAll(query.build().toMap());
            return this;
        }

        @Override
        public BasicDBObject build() {
            return where;
        }

    }

    public static class IN implements KeyMark, IQuery {

        private BasicDBList where = new BasicDBList();
        private String field;

        public IN(String field) {
            this.field = field;
        }

        public IN add(Object val) {
            where.add(val);
            return this;
        }

        @Override
        public BasicDBObject build() {
            BasicDBObject obj = new BasicDBObject();
            obj.put(field, new BasicDBObject(name(), where));
            return obj;
        }

        @Override
        public String name() {
            return "$in";
        }

    }

    public static enum OP {
        UNEQUAL("$ne"), BIGGER("$gt"), SMALLER("$lt"), BIGGER_EQUAL("$gte"), SMALLER_EQUAL("$lte");

        private String value;

        private OP(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static class COMPARE implements IQuery {
        private String key;
        private Object val1;
        private OP op1;
        private Object val2;
        private OP op2;

        public COMPARE(String key) {
            this.key = key;
        }

        @Override
        public BasicDBObject build() {
            BasicDBObject query = new BasicDBObject();
            query.put(op1.getValue(), val1);
            if (op2 != null && val2 != null) {
                query.put(op2.getValue(), val2);
            }
            return new BasicDBObject(key, query);
        }

        public void put(OP op1, Object val1, OP op2, Object val2) {
            this.op1 = op1;
            this.val1 = val1;
            this.op2 = op2;
            this.val2 = val2;
        }

    }

}
