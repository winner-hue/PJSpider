package icu.fanjie;

import java.util.AbstractQueue;
import java.util.concurrent.ThreadPoolExecutor;

public abstract class SpiderScheduler {
    protected SpiderTracker spiderTrackers = null;
    protected int threadCount;
    protected Storage storage;
    protected int requestsNum;
    protected int failRequestsNum;
    protected int dataNum;
    protected int currentActiveThreadNum;
    protected ThreadPoolExecutor threadPool;
    protected boolean isDup;
    protected Queue queue;
    protected int status;
}
