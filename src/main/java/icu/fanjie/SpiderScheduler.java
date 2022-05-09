package icu.fanjie;

public interface SpiderScheduler {
    void createQueue();

//    void createStorage();

//    void createThreadPool();

    void start();

    void monitor();

    void run();

    void stop();

    boolean storage(Object o);

    boolean addSpiderTracker(SpiderTracker tracker);

}
