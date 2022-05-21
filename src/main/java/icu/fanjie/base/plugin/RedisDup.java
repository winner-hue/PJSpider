package icu.fanjie.base.plugin;

import icu.fanjie.SpiderTracker;
import icu.fanjie.base.BaseDup;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;

public class RedisDup extends BaseDup {
    protected String host = "127.0.0.1";
    protected int port = 6379;
    protected int db = 0;
    protected String password = "root";
    protected String dupName = "spider_" + System.currentTimeMillis();
    protected JedisPool jedisPool = null;

    public RedisDup() {
        createRedisClient();
    }

    public RedisDup(String host, int port, int db, String password, String dupName) {
        this.host = host;
        this.port = port;
        this.db = db;
        this.password = password;
        this.dupName = dupName;
        createRedisClient();
    }

    synchronized protected void createRedisClient() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(5);
        config.setMaxIdle(3);
        jedisPool = new JedisPool(config, host, port, 10, password);
    }

    @Override
    synchronized public boolean isExist(Object o) {
        Jedis resource = jedisPool.getResource();
        boolean sismember = resource.sismember(dupName, o.toString());
        resource.close();
        return sismember;
    }

    @Override
    synchronized public boolean add(Object o) {
        Jedis resource = jedisPool.getResource();
        long sadd = resource.sadd(dupName, o.toString(), "1");
        resource.close();
        return true;
    }

    @Override
    synchronized public boolean remove(Object o) {
        Jedis resource = jedisPool.getResource();
        long srem = resource.srem(dupName, o.toString());
        resource.close();
        return true;
    }

    @Override
    synchronized public boolean clean() {
        Jedis resource = jedisPool.getResource();
        long del = resource.del(dupName);
        resource.close();
        return true;
    }

    @Override
    synchronized public List<SpiderTracker> dup(SpiderTracker spiderTracker) {
        return super.dup(spiderTracker);
    }


    @Override
    synchronized public void destroy() {
        Jedis resource = jedisPool.getResource();
        resource.del(dupName);
        resource.close();
    }
}
