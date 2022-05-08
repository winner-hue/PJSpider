package icu.fanjie.base;

import icu.fanjie.Queue;

import java.util.concurrent.LinkedBlockingQueue;

public class BaseQueue<E> implements Queue<E> {
    protected LinkedBlockingQueue<E> blockingQueue = null;

    public BaseQueue(int size) {
        blockingQueue = new LinkedBlockingQueue<>(size);
    }

    @Override
    public boolean add(E e) {
        return blockingQueue.offer(e);
    }

    @Override
    public E remove() {
        return blockingQueue.remove();
    }

    @Override
    public boolean remove(E e) {
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
    public E get() {
        if (blockingQueue.size() > 0) {
            return blockingQueue.poll();
        }
        return null;
    }

}
