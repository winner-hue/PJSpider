package icu.fanjie.base;

import com.alibaba.fastjson.JSONObject;
import icu.fanjie.SpiderTracker;
import icu.fanjie.Storage;

public class PrintStorage implements Storage {

    @Override
    public void storage(SpiderTracker tracker) {
        Object parser = tracker.getExtraParams().get("parser");
        JSONObject jo = (JSONObject) parser;
        for (String key : jo.keySet()) {
            System.out.println(key + ": " + jo.get(key));
        }
    }
}
