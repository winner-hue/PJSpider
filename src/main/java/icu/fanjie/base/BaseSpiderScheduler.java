package icu.fanjie.base;

import icu.fanjie.*;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BaseSpiderScheduler implements SpiderScheduler {
    protected Queue<SpiderTracker> spiderTrackers;
    protected int threadCount = 1;
    protected Storage storage;
    protected int requestsNum;
    protected int failRequestsNum;
    protected int dataNum;
    protected int currentActiveThreadNum;
    protected ThreadPoolExecutor threadPool;
    protected int blockingQueueSize;
    protected boolean isDup;
    protected Queue<String> dupQueue;
    protected int status;

    public BaseSpiderScheduler() {
        spiderTrackers = new BaseQueue<>();
        dupQueue = new BaseQueue<>();
        storage = new BaseStorage();
        blockingQueueSize = 100;
        LinkedBlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(blockingQueueSize);
        threadPool = new ThreadPoolExecutor(threadCount, threadCount, 10, TimeUnit.SECONDS, blockingQueue);

    }

    @Override
    public void createQueue() {

    }

    public static void main(String[] args) {
        BaseSpiderScheduler spiderScheduler = new BaseSpiderScheduler();
        for (int i = 0; i < 100; i++) {
            while (spiderScheduler.threadPool.getQueue().size() >= spiderScheduler.blockingQueueSize) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            spiderScheduler.threadPool.submit(new TestDemo(String.valueOf(i)));
        }
        spiderScheduler.threadPool.shutdown();
    }

    public Queue<SpiderTracker> getSpiderTrackers() {
        return spiderTrackers;
    }

    public void setSpiderTrackers(Queue<SpiderTracker> spiderTrackers) {
        this.spiderTrackers = spiderTrackers;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public int getRequestsNum() {
        return requestsNum;
    }

    public void setRequestsNum(int requestsNum) {
        this.requestsNum = requestsNum;
    }

    public int getFailRequestsNum() {
        return failRequestsNum;
    }

    public void setFailRequestsNum(int failRequestsNum) {
        this.failRequestsNum = failRequestsNum;
    }

    public int getDataNum() {
        return dataNum;
    }

    public void setDataNum(int dataNum) {
        this.dataNum = dataNum;
    }

    public int getCurrentActiveThreadNum() {
        return currentActiveThreadNum;
    }

    public void setCurrentActiveThreadNum(int currentActiveThreadNum) {
        this.currentActiveThreadNum = currentActiveThreadNum;
    }

    public ThreadPoolExecutor getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(ThreadPoolExecutor threadPool) {
        this.threadPool = threadPool;
    }

    public boolean isDup() {
        return isDup;
    }

    public void setDup(boolean dup) {
        isDup = dup;
    }

    public Queue<String> getDupQueue() {
        return dupQueue;
    }

    public void setDupQueue(Queue<String> dupQueue) {
        this.dupQueue = dupQueue;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
