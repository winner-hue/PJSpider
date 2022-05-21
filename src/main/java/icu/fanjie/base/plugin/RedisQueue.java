package icu.fanjie.base.plugin;

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
    public boolean add(Object e) {
        Jedis resource = jedisPool.getResource();
        long rpush = resource.rpush(spiderQueue, e.toString());
        resource.close();
        return rpush == 1;
    }

    @Override
    public Object remove() {
        Jedis resource = jedisPool.getResource();
        String lpop = resource.lpop(spiderQueue);
        resource.close();
        return lpop;
    }

    @Override
    public boolean remove(Object e) {
        return blockingQueue.remove(e);
    }

    @Override
    public int size() {
        return blockingQueue.size();
    }

    @Override
    public boolean isEmpty() {
        return blockingQueue.isEmpty();
    }

    @Override
    public Object get() {
        if (blockingQueue.size() > 0) {
            return blockingQueue.poll();
        }
        return null;
    }
}
