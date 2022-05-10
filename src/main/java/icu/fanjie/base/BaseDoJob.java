package icu.fanjie.base;

import icu.fanjie.*;

import java.util.List;

public class BaseDoJob implements Runnable {
    protected SpiderTracker tracker;
    protected Dup<Object> dup;
    protected Storage storage;
    protected Queue<SpiderTracker> spiderTrackerQueue;

    public BaseDoJob(SpiderTracker tracker, Dup<Object> dup, Storage storage, Queue<SpiderTracker> spiderTrackerQueue) {
        this.tracker = tracker;
        this.dup = dup;
        this.storage = storage;
        this.spiderTrackerQueue = spiderTrackerQueue;
    }

    public BaseDoJob(SpiderTracker tracker, Storage storage, Queue<SpiderTracker> spiderTrackerQueue) {
        this.tracker = tracker;
        this.storage = storage;
        this.spiderTrackerQueue = spiderTrackerQueue;
    }

    @Override
    public void run() {
        Downloader downloader = tracker.getDownloader();
        Parser parser = tracker.getParser();
        downloader.download(tracker);
        parser.parser(tracker);
        storage.storage(tracker);
        if (dup != null) {
            List<SpiderTracker> dup = this.dup.dup(tracker);
            addSpiderTracker(tracker, dup);
        } else {
            addSpiderTracker(tracker);
        }
    }

    public void addSpiderTracker(SpiderTracker tracker, List<SpiderTracker> spiderTrackers) {

    }

    public void addSpiderTracker(SpiderTracker tracker) {

    }

    public SpiderTracker getTracker() {
        return tracker;
    }

    public void setTracker(SpiderTracker tracker) {
        this.tracker = tracker;
    }

    public Dup<Object> getDup() {
        return dup;
    }

    public void setDup(Dup<Object> dup) {
        this.dup = dup;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}
