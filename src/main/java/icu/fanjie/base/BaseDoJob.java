package icu.fanjie.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import icu.fanjie.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class BaseDoJob implements Runnable {
    protected SpiderTracker tracker;
    protected Dup<Object> dup;
    protected Storage storage;
    protected Queue spiderTrackerQueue;

    public BaseDoJob(SpiderTracker tracker, Dup<Object> dup, Storage storage, Queue spiderTrackerQueue) {
        this.tracker = tracker;
        this.dup = dup;
        this.storage = storage;
        this.spiderTrackerQueue = spiderTrackerQueue;
    }

    public BaseDoJob(SpiderTracker tracker, Storage storage, Queue spiderTrackerQueue) {
        this.tracker = tracker;
        this.storage = storage;
        this.spiderTrackerQueue = spiderTrackerQueue;
    }

    @Override
    public void run() {
        Downloader downloader = tracker.getDownloader();
        Parser parser = tracker.getParser();
        downloader.download(tracker);
        parser.process(tracker);
        storage.storage(tracker);
        if (dup != null) {
            List<SpiderTracker> dup = this.dup.dup(tracker);
            addSpiderTracker(dup);
        } else {
            addSpiderTracker(tracker);
        }
    }

    public void addSpiderTracker(List<SpiderTracker> spiderTrackers) {
        List<SpiderTracker> collect = spiderTrackers.stream().sorted(Comparator.comparing(SpiderTracker::getPriority).reversed()).collect(Collectors.toList());
        for (SpiderTracker spiderTracker : collect) {
            spiderTrackerQueue.add(spiderTracker);
        }
    }

    public void addSpiderTracker(SpiderTracker tracker) {
        HashMap<String, Object> extraParams = tracker.getExtraParams();
        Object parser = extraParams.get("parser");
        if (parser != null) {
            JSONObject jo = (JSONObject) parser;
            JSONArray trackers = jo.getJSONArray("target_requests");
            for (Object tmpTracker : trackers) {
                SpiderTracker task = (SpiderTracker) tmpTracker;
                while (!spiderTrackerQueue.add(task)) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
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
