package icu.fanjie.base;

import icu.fanjie.*;

public class BaseDoJob implements Runnable {
    protected SpiderTracker tracker;
    protected Dup<Object> dup;
    protected Storage storage;

    public BaseDoJob(SpiderTracker tracker, Dup<Object> dup, Storage storage) {
        this.tracker = tracker;
        this.dup = dup;
        this.storage = storage;
    }

    public BaseDoJob(SpiderTracker tracker, Storage storage) {
        this.tracker = tracker;
        this.storage = storage;
    }

    @Override
    public void run() {
        Downloader downloader = tracker.getDownloader();
        Parser parser = tracker.getParser();
        downloader.download(tracker);
        parser.parser(tracker);
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
