package icu.fanjie;

import icu.fanjie.base.BaseDownloader;
import icu.fanjie.base.BaseNewsParser;
import icu.fanjie.base.BaseSpiderScheduler;

public class TestDemo {
    public static void main(String[] args) {
        BaseSpiderScheduler scheduler = new BaseSpiderScheduler();
        SpiderTracker spiderTracker = new SpiderTracker();
        Downloader downloader = new BaseDownloader();
        Parser parser = new BaseNewsParser();
        spiderTracker.setDownloader(downloader);
        spiderTracker.setParser(parser);
        spiderTracker.setSeed("https://blog.csdn.net/qq_52423918/article/details/122671378");
        spiderTracker.setPriority(1);
        scheduler.getSpiderTrackers().add(spiderTracker);
        scheduler.setTaskName("csdn");
        scheduler.start();
    }
}
