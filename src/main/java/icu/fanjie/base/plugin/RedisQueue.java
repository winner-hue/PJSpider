package icu.fanjie.base.plugin;

import icu.fanjie.base.BaseQueue;

public class RedisQueue extends BaseQueue {
    public RedisQueue(int size) {
        super(size);
    }

    @Override
    public boolean add(Object e) {
        return blockingQueue.offer(e);
    }

    @Override
    public Object remove() {
        return blockingQueue.remove();
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
