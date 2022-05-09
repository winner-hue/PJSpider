package icu.fanjie;

import java.util.concurrent.ThreadPoolExecutor;

public class SpiderMonitor implements Runnable {
    protected Queue<SpiderTracker> spiderTrackerQueue;
    protected ThreadPoolExecutor threadPool;

    public SpiderMonitor(Queue<SpiderTracker> spiderTrackerQueue, ThreadPoolExecutor threadPool) {
        this.spiderTrackerQueue = spiderTrackerQueue;
        this.threadPool = threadPool;
    }

    @Override
    public void run() {
        while (spiderTrackerQueue.size() != 0 || threadPool.getActiveCount() != 0) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Queue<SpiderTracker> getSpiderTrackerQueue() {
        return spiderTrackerQueue;
    }

    public void setSpiderTrackerQueue(Queue<SpiderTracker> spiderTrackerQueue) {
        this.spiderTrackerQueue = spiderTrackerQueue;
    }

    public ThreadPoolExecutor getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(ThreadPoolExecutor threadPool) {
        this.threadPool = threadPool;
    }

}
