package icu.fanjie.base;

import icu.fanjie.*;

import java.util.HashSet;
import java.util.Set;

public class BaseDownloader implements Downloader {
    protected String method = "";
    protected Headers headers = new Headers("user-agent:" + new JUA().getChromeUA());
    protected Data postBody = null;
    protected Proxies proxies = null;
    protected int retryCount = 3;
    protected Set<Integer> acceptStatusCode = new HashSet<>();

    public BaseDownloader() {
        System.out.println(acceptStatusCode.toString());
    }

    public static void main(String[] args) {
        new BaseDownloader();
    }

    @Override
    public String download() {
        return null;
    }
}
