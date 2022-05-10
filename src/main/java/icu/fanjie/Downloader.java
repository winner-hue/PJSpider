package icu.fanjie;

import okhttp3.ConnectionSpec;

import java.util.List;

public interface Downloader {
    void download(SpiderTracker tracker);

    Headers getHeaders();

    Proxies getProxies();

    Data getData();

    List<ConnectionSpec> getConnectionSpecList();
}
