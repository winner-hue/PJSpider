package icu.fanjie.base.plugin;

import icu.fanjie.SpiderTracker;
import icu.fanjie.base.PrintStorage;

public class MongoDBStorage extends PrintStorage {
    protected String host = "127.0.0.1";
    protected int port = 3306;
    protected String user = "root";
    protected String password = "root";

    public MongoDBStorage() {
    }

    @Override
    public void storage(SpiderTracker tracker) {
        super.storage(tracker);
    }
}
