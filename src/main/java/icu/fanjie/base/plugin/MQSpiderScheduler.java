package icu.fanjie.base.plugin;

import icu.fanjie.Dup;
import icu.fanjie.Queue;
import icu.fanjie.Storage;
import icu.fanjie.base.BaseSpiderScheduler;

import java.util.concurrent.ThreadPoolExecutor;

public class MQSpiderScheduler extends BaseSpiderScheduler {
    public MQSpiderScheduler() {
    }

    public MQSpiderScheduler(String taskName, Queue spiderTrackers, int taskSize, int threadCount, Storage storage, ThreadPoolExecutor threadPool, int blockingQueueSize, boolean isDup, Dup dupQueue) {
        super(taskName, spiderTrackers, taskSize, threadCount, storage, threadPool, blockingQueueSize, isDup, dupQueue);
    }

    @Override
    public void createQueue() {
        super.createQueue();
    }

    @Override
    public void createStorage() {
        super.createStorage();
    }

    @Override
    public void createThreadPool() {
        super.createThreadPool();
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void monitor() {
        super.monitor();
    }

    @Override
    public void run() {
        super.run();
    }

    @Override
    public void stop() {
        super.stop();
    }
}
