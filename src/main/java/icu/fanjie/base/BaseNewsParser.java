package icu.fanjie.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import icu.fanjie.Parser;
import icu.fanjie.SpiderTracker;

import java.util.List;

public class BaseNewsParser implements Parser {

    protected String getTitle() {
        return null;
    }

    protected String getSubTitle() {
        return null;
    }

    protected String getContent() {
        return null;
    }

    protected String getAuthor() {
        return null;
    }

    protected List<String> getImages() {
        return null;
    }

    protected String getPublishTime() {
        return null;
    }

    protected String getUpdateTime() {
        return null;
    }

    protected int getCommentNum() {
        return 0;
    }

    /**
     * 点赞数
     *
     * @return int
     */
    protected int getLikeNum() {
        return 0;
    }

    protected JSONArray getCommentContent() {
        return null;
    }

    @Override
    public void parser(SpiderTracker spiderTracker) {
        String html = spiderTracker.getHtml();

        JSONObject jo = new JSONObject();

        jo.put("parser", jo);
    }

}
