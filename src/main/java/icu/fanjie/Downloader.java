package icu.fanjie;

import java.util.HashSet;
import java.util.Set;

public abstract class Downloader {
    protected String method = "";
    protected Headers headers = new Headers("user-agent:" + new JUA().getChromeUA());
    protected Data postBody = null;
    protected Proxies proxies = null;
    protected int retryCount = 3;
    protected Set<Integer> acceptStatusCode = new HashSet<>();

    public Downloader() {
        acceptStatusCode.add(200);
    }

    protected Object download() {
        return null;
    }
}
