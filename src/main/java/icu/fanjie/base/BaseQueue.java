package icu.fanjie.base;

import icu.fanjie.Queue;
import icu.fanjie.SpiderTracker;

import java.util.concurrent.LinkedBlockingQueue;

public class BaseQueue implements Queue {
    protected LinkedBlockingQueue<Object> blockingQueue = null;

    public BaseQueue(int size) {
        blockingQueue = new LinkedBlockingQueue<>(size);
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
