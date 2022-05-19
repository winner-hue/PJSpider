package icu.fanjie.base.plugin;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import icu.fanjie.CommonUtil;
import icu.fanjie.SpiderTracker;
import icu.fanjie.base.PrintStorage;
import org.bson.Document;

public class MongoDBStorage extends PrintStorage {
    protected String host = "127.0.0.1";
    protected int port = 27017;
    protected String user = "root";
    protected String password = "root";
    protected String dbName = "spider";
    protected String tableName = "spider";
    protected MongoClient client = null;
    protected MongoDatabase database = null;

    public MongoDBStorage(String host, int port, String user, String password, String dbName, String tableName) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.dbName = dbName;
        this.tableName = tableName;
        createMongoClient();
    }

    public MongoDBStorage() {
        createMongoClient();
    }

    protected void createMongoClient() {
        ServerAddress serverAddress = new ServerAddress(host, port);
        MongoCredential mongoCredential = MongoCredential.createCredential(user, "admin", password.toCharArray());
        client = MongoClients.create(MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString("mongodb://" + serverAddress.toString()))
                .credential(mongoCredential)
                .build(), null);
        database = client.getDatabase(dbName);
        database.createCollection(tableName);
    }


    @Override
    public void storage(SpiderTracker tracker) {
        Object parser = tracker.getExtraParams().get("parser");
        JSONObject jo = (JSONObject) parser;
        JSONObject parser_content = jo.getJSONObject("parser_content");
        if (parser_content == null || parser_content.size() == 0) {
            return;
        }
        parser_content.put("_id", CommonUtil.getMd5(parser_content.toJSONString()));
        MongoCollection<Document> collection = database.getCollection(tableName);
        collection.insertOne(Document.parse(parser_content.toJSONString()));
    }
}
