package icu.fanjie;

import icu.fanjie.base.BaseDownloader;
import icu.fanjie.base.BaseParser;

import java.util.HashMap;

public class SpiderTracker {
    protected Downloader downloader;
    protected Parser parser;
    protected String seed;
    protected String previousSeed;
    protected HashMap<String, Object> extraParams;
    protected int priority;


    public SpiderTracker() {
        this.downloader = new BaseDownloader();
        this.parser = new BaseParser();
    }


    public SpiderTracker(Downloader downloader, Parser parser) {
        this.downloader = downloader;
        this.parser = parser;
    }

    protected void run() {
        this.downloader.download();
        this.parser.parser();
    }

    public Downloader getDownloader() {
        return downloader;
    }

    public void setDownloader(Downloader downloader) {
        this.downloader = downloader;
    }

    public Parser getParser() {
        return parser;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public String getPreviousSeed() {
        return previousSeed;
    }

    public void setPreviousSeed(String previousSeed) {
        this.previousSeed = previousSeed;
    }

    public HashMap<String, Object> getExtraParams() {
        return extraParams;
    }

    public void setExtraParams(HashMap<String, Object> extraParams) {
        this.extraParams = extraParams;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
