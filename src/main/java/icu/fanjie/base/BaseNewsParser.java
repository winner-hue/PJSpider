package icu.fanjie.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import icu.fanjie.Parser;
import icu.fanjie.SpiderTracker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

public class BaseNewsParser extends Parser {

    protected String getTitle(Document soup, String html) {
        Elements title = soup.select("title");
        if (title.size() > 0) {
            return title.get(0).toString();
        }
        return null;
    }

    protected String getSubTitle(Document soup, String html) {
        return null;
    }

    protected String getContent(Document soup, String html) {
        return null;
    }

    protected String getAuthor(Document soup, String html) {
        return null;
    }

    protected List<String> getImages(Document soup, String html) {
        return null;
    }

    protected String getPublishTime(Document soup, String html) {
        return null;
    }

    protected String getUpdateTime(Document soup, String html) {
        return null;
    }

    protected int getCommentNum(Document soup, String html) {
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
        Object parser_type = spiderTracker.getExtraParams().get("parser_type");
        Document soup = Jsoup.parse(html);
        JSONObject jo = new JSONObject();
        if (parser_type.equals("list")) {
            Elements elements = soup.select("div.Community-item-active a.blog");
            JSONArray ja = new JSONArray();
            for (int i = 0; i < 3; i++) {
                String href = elements.get(i).attr("href");
                SpiderTracker tracker = new SpiderTracker();
                tracker.setSeed(href);
                tracker.setPreviousSeed(spiderTracker.getSeed());
                tracker.getExtraParams().put("parser_type", "detail");
                tracker.setPriority(2);
                ja.add(tracker);
            }
            jo.put("trackers", ja);
        }
        if (parser_type.equals("detail")) {
            JSONObject parser_content = new JSONObject();
            String title = getTitle(soup, html);
            parser_content.put("title", title);
            jo.put("parser_content", parser_content);
        }

        spiderTracker.getExtraParams().put("parser", jo);
    }

}
