package icu.fanjie.base;

import icu.fanjie.*;
import okhttp3.ConnectionSpec;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BaseDownloader implements Downloader {
    protected String method = "get";
    protected Headers headers = new Headers("user-agent:" + new JUA().getChromeUA());
    protected Data postBody = null;
    protected Proxies proxies = null;
    protected int retryCount = 3;
    protected Set<Integer> acceptStatusCode = new HashSet<>();

    public BaseDownloader() {
        acceptStatusCode.add(200);
    }

    public BaseDownloader(String method, Headers headers, Data postBody, Proxies proxies, int retryCount, Set<Integer> acceptStatusCode) {
        this.method = method;
        this.headers = headers;
        this.postBody = postBody;
        this.proxies = proxies;
        this.retryCount = retryCount;
        this.acceptStatusCode = acceptStatusCode;
        acceptStatusCode.add(200);
    }

    boolean isInAcceptStatusCode(int status) {
        return acceptStatusCode.contains(status);
    }

    @Override
    public void download(SpiderTracker tracker) {
        String url = tracker.getSeed();
        for (int i = 0; i < retryCount; i++) {
            try {
                if (method.equalsIgnoreCase("get")) {
                    Response resp = Requests.get(url, null, headers, 10000, true, proxies, null, true);
                    if (getResponse(tracker, resp)) break;
                }
                if (method.equalsIgnoreCase("post")) {
                    Response resp = Requests.post(url, null, postBody, headers, 10000, true, proxies, null, true);
                    if (getResponse(tracker, resp)) break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean getResponse(SpiderTracker tracker, Response resp) {
        if (isInAcceptStatusCode(resp.statusCode)) {
            tracker.setHtml(resp.getHtml());
            HashMap<String, Object> extraParams = tracker.getExtraParams();
            if (extraParams == null) {
                extraParams = new HashMap<>();
                extraParams.put("downloader", resp);
                tracker.setExtraParams(extraParams);
            } else {
                extraParams.put("downloader", resp);
            }
            return true;
        }
        return false;
    }

    @Override
    public Headers getHeaders() {
        return null;
    }

    @Override
    public Proxies getProxies() {
        return null;
    }

    @Override
    public icu.fanjie.Data getData() {
        return null;
    }

    @Override
    public List<ConnectionSpec> getConnectionSpecList() {
        return null;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public Data getPostBody() {
        return postBody;
    }

    public void setPostBody(Data postBody) {
        this.postBody = postBody;
    }

    public void setProxies(Proxies proxies) {
        this.proxies = proxies;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public Set<Integer> getAcceptStatusCode() {
        return acceptStatusCode;
    }

    public void setAcceptStatusCode(Set<Integer> acceptStatusCode) {
        this.acceptStatusCode = acceptStatusCode;
    }
}
