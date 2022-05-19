package icu.fanjie.base.plugin;

import icu.fanjie.SpiderTracker;
import icu.fanjie.base.BaseDup;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;

public class RedisDup<E> extends BaseDup<E> {
    protected String host = "127.0.0.1";
    protected int port = 6379;
    protected int db = 0;
    protected String password = "root";
    protected String dupName = "spider";
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

    protected void createRedisClient() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(5);
        config.setMaxIdle(3);
        jedisPool = new JedisPool(config, host, port, 10, password);
    }

    @Override
    public boolean isExist(Object o) {
        return jedisPool.getResource().sismember(dupName, o.toString());

    }

    @Override
    public boolean add(Object o) {
        long sadd = jedisPool.getResource().sadd(dupName, o.toString(), "1");
        return sadd == 0;
    }

    @Override
    public boolean remove(Object o) {
        long srem = jedisPool.getResource().srem(dupName, o.toString());
        return srem == 0;
    }

    @Override
    public boolean clean() {
        long del = jedisPool.getResource().del(dupName);
        return del == 0;
    }

    @Override
    public List<SpiderTracker> dup(SpiderTracker spiderTracker) {
        return super.dup(spiderTracker);
    }


    @Override
    public void destroy() {
        jedisPool.getResource().del(dupName);
    }
}
