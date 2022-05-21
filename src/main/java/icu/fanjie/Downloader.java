package icu.fanjie;

import okhttp3.ConnectionSpec;

import java.io.Serializable;
import java.util.List;

public interface Downloader extends Serializable {
    void download(SpiderTracker tracker);

    Headers getHeaders();

    Proxies getProxies();

    Data getData();

    List<ConnectionSpec> getConnectionSpecList();
}
