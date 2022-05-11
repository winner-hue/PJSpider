package icu.fanjie;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Parser {
    protected void parser(SpiderTracker spiderTracker) {

    }

    public void process(SpiderTracker spiderTracker) {
        parser(spiderTracker);
        JSONObject parser = (JSONObject) spiderTracker.getExtraParams().get("parser");
        JSONArray targetRequests = parser.getJSONArray("target_requests");
        if (targetRequests == null) {
            return;
        }
        JSONArray ja = new JSONArray();
        for (Object targetRequest : targetRequests) {
            SpiderTracker tracker = (SpiderTracker) targetRequest;
            if (tracker.getParser() == null) {
                tracker.setParser(spiderTracker.parser);
            }
            if (tracker.getDownloader() == null) {
                tracker.setDownloader(spiderTracker.downloader);
            }
            ja.add(tracker);
        }
        parser.put("target_requests", ja);
    }
}
