package icu.fanjie.base;

import icu.fanjie.*;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BaseSpiderScheduler implements SpiderScheduler {
    protected String taskName = "BaseSpider";
    protected Queue<SpiderTracker> spiderTrackers;
    protected int taskSize = 100 * 10000;
    protected int threadCount = 10;
    protected Storage storage;
    protected int requestsNum;
    protected int failRequestsNum;
    protected int dataNum;
    protected int currentActiveThreadNum;
    protected ThreadPoolExecutor threadPool;
    protected int blockingQueueSize = 100;
    protected boolean isDup = true;
    protected Dup<Object> dupQueue;
    protected int status = 1;

    public BaseSpiderScheduler() {
        createQueue();
        createStorage();
        createThreadPool();
    }

    public BaseSpiderScheduler(String taskName, Queue<SpiderTracker> spiderTrackers, int taskSize, int threadCount, Storage storage, ThreadPoolExecutor threadPool, int blockingQueueSize, boolean isDup, Dup<Object> dupQueue) {
        this.taskName = taskName;
        this.spiderTrackers = spiderTrackers;
        this.taskSize = taskSize;
        this.threadCount = threadCount;
        this.storage = storage;
        this.threadPool = threadPool;
        this.blockingQueueSize = blockingQueueSize;
        this.isDup = isDup;
        this.dupQueue = dupQueue;
        createQueue();
    }

    @Override
    public void createQueue() {
        spiderTrackers = new BaseQueue<>(taskSize);
        if (isDup) {
            dupQueue = new BaseDup<>();
        }
    }

    public void createStorage() {
        storage = new BaseStorage();
    }

    public void createThreadPool() {
        LinkedBlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(blockingQueueSize);
        threadPool = new ThreadPoolExecutor(threadCount, threadCount, 10, TimeUnit.SECONDS, blockingQueue);
    }

    @Override
    public void start() {
        monitor();

    }

    @Override
    public void monitor() {
        SpiderMonitor spiderMonitor = new SpiderMonitor(spiderTrackers, threadPool);

        Thread thread = new Thread(spiderMonitor);
        thread.setName(taskName);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean storage(Object o) {
        return false;
    }

    @Override
    public boolean addSpiderTracker(SpiderTracker tracker) {
        return false;
    }

    public int getTaskSize() {
        return taskSize;
    }

    public void setTaskSize(int taskSize) {
        this.taskSize = taskSize;
    }

    public int getBlockingQueueSize() {
        return blockingQueueSize;
    }

    public void setBlockingQueueSize(int blockingQueueSize) {
        this.blockingQueueSize = blockingQueueSize;
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

    public Dup<Object> getDupQueue() {
        return dupQueue;
    }

    public void setDupQueue(Dup<Object> dupQueue) {
        this.dupQueue = dupQueue;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
