package icu.fanjie.base;

import icu.fanjie.*;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BaseSpiderScheduler {
    protected String taskName = "BaseSpider";
    protected Queue spiderTrackers;
    protected int taskSize = 100 * 10000;
    protected int threadCount = 10;
    protected Storage storage;
    public static int requestsNum;
    public static int failRequestsNum;
    public static int dataNum;
    public static int currentActiveThreadNum;
    protected ThreadPoolExecutor threadPool;
    protected int blockingQueueSize = 100;
    protected boolean isDup = true;
    protected Dup<Object> dupQueue;
    public static int status = 1;

    public BaseSpiderScheduler() {
        createQueue();
        createStorage();
        createThreadPool();
    }

    public BaseSpiderScheduler(String taskName, Queue spiderTrackers, int taskSize, int threadCount, Storage storage, ThreadPoolExecutor threadPool, int blockingQueueSize, boolean isDup, Dup<Object> dupQueue) {
        this.taskName = taskName;
        this.spiderTrackers = spiderTrackers;
        this.taskSize = taskSize;
        this.threadCount = threadCount;
        this.storage = storage;
        this.threadPool = threadPool;
        this.blockingQueueSize = blockingQueueSize;
        this.isDup = isDup;
        this.dupQueue = dupQueue;
    }

    public void createQueue() {
        spiderTrackers = new BaseQueue(taskSize);
        if (isDup) {
            dupQueue = new BaseDup<>();
        }
    }

    public void createStorage() {
        storage = new PrintStorage();
    }

    public void createThreadPool() {
        LinkedBlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(blockingQueueSize);
        threadPool = new ThreadPoolExecutor(threadCount, threadCount, 10, TimeUnit.SECONDS, blockingQueue);
    }

    public void start() {
//        monitor();
        run();
        threadPool.shutdown();
    }

    public void monitor() {
        SpiderMonitor spiderMonitor = new SpiderMonitor(spiderTrackers, threadPool);
        Thread thread = new Thread(spiderMonitor);
        thread.setName(taskName);
        thread.start();
    }

    public void run() {
        status = 2;
        while (spiderTrackers.size() != 0 || threadPool.getActiveCount() != 0) {
            if (spiderTrackers.size() > 0) {
                SpiderTracker tracker = (SpiderTracker) spiderTrackers.get();
                BaseDoJob doJob = new BaseDoJob(tracker, storage, spiderTrackers);
                if (isDup) {
                    doJob = new BaseDoJob(tracker, dupQueue, storage, spiderTrackers);
                }
                while (threadPool.getQueue().size() >= blockingQueueSize) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                threadPool.submit(doJob);
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void stop() {
        spiderTrackers.remove();
        threadPool.shutdown();
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

    public Queue getSpiderTrackers() {
        return spiderTrackers;
    }

    public void setSpiderTrackers(Queue spiderTrackers) {
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

    public int getCurrentActiveThreadNum() {
        return threadPool.getActiveCount();
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

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
