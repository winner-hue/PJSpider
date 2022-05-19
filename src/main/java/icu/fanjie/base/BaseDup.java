package icu.fanjie.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import icu.fanjie.CommonUtil;
import icu.fanjie.Dup;
import icu.fanjie.SpiderTracker;

import java.util.*;

public class BaseDup implements Dup {

    protected Set<Object> objects;

    public BaseDup() {
        objects = Collections.synchronizedSet(new HashSet<>());
    }

    @Override
    public boolean isExist(Object o) {
        return objects.contains(o);
    }

    @Override
    public boolean add(Object o) {
        return objects.add(o);
    }

    @Override
    public boolean remove(Object o) {
        return objects.remove(o);
    }

    @Override
    public boolean clean() {
        try {
            objects.clear();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public List<SpiderTracker> dup(SpiderTracker spiderTracker) {
        List<SpiderTracker> spiderTrackers = new ArrayList<>();
        HashMap<String, Object> extraParams = spiderTracker.getExtraParams();
        Object parser = extraParams.get("parser");
        if (parser != null) {
            JSONObject jo = (JSONObject) parser;
            JSONArray trackers = jo.getJSONArray("trackers");
            for (Object o : trackers) {
                SpiderTracker tracker = (SpiderTracker) o;
                String url = tracker.getSeed();
                String md5Url = CommonUtil.getMd5(url);
                boolean add = add(md5Url);
                if (add) {
                    spiderTrackers.add(tracker);
                }
            }
        }
        return spiderTrackers;
    }

    @Override
    public void destroy() {
        objects = null;
    }

}
