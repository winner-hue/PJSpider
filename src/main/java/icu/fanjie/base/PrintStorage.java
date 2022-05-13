package icu.fanjie.base;

import com.alibaba.fastjson.JSONObject;
import icu.fanjie.SpiderTracker;
import icu.fanjie.Storage;

public class PrintStorage implements Storage {

    @Override
    public void storage(SpiderTracker tracker) {
        Object parser = tracker.getExtraParams().get("parser");
        JSONObject jo = (JSONObject) parser;
        JSONObject parser_content = jo.getJSONObject("parser_content");
        if (parser_content != null) {
            for (String key : parser_content.keySet()) {
                System.out.println(key + ": " + parser_content.get(key));
            }
        }
    }
}
