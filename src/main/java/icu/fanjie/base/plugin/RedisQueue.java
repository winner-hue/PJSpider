package icu.fanjie.base.plugin;

import icu.fanjie.CommonUtil;
import icu.fanjie.base.BaseQueue;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisQueue extends BaseQueue {
    protected String host = "127.0.0.1";
    protected int port = 6379;
    protected int db = 0;
    protected String password = "root";
    protected String spiderQueue = "spider_queue_" + System.currentTimeMillis();
    protected JedisPool jedisPool = null;


    protected void createRedisClient() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(5);
        config.setMaxIdle(3);
        jedisPool = new JedisPool(config, host, port, 10, password);
    }

    public RedisQueue() {
        createRedisClient();
    }

    public RedisQueue(String host, int port, int db, String password, String spiderQueue) {
        this.host = host;
        this.port = port;
        this.db = db;
        this.password = password;
        this.spiderQueue = spiderQueue;
        createRedisClient();
    }

    @Override
    synchronized public boolean add(Object e) {
        Jedis resource = jedisPool.getResource();
        byte[] serialize = CommonUtil.serialize(e);
        long rpush = resource.rpush(spiderQueue.getBytes(), serialize);
        resource.close();
        return rpush == 1;
    }

    @Override
    synchronized public Object remove() {
        Jedis resource = jedisPool.getResource();
        long del = resource.del(spiderQueue.getBytes());
        resource.close();
        return del == 1;
    }

    @Override
    synchronized public boolean remove(Object e) {
        return false;
    }

    @Override
    synchronized public int size() {
        Jedis resource = jedisPool.getResource();
        long scard = resource.llen(spiderQueue.getBytes());
        resource.close();
        return (int) scard;
    }

    @Override
    synchronized public boolean isEmpty() {
        Jedis resource = jedisPool.getResource();
        long scard = resource.llen(spiderQueue.getBytes());
        resource.close();
        return scard == 0;
    }

    @Override
    synchronized public Object get() {
        Jedis resource = jedisPool.getResource();
        long llen = resource.llen(spiderQueue.getBytes());
        if (llen > 0) {
            byte[] bytes = resource.lpop(spiderQueue.getBytes());
            resource.close();
            return CommonUtil.unserialize(bytes);
        }
        resource.close();
        return null;
    }
}
