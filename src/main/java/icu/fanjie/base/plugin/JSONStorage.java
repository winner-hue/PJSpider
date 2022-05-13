package icu.fanjie.base.plugin;

import com.alibaba.fastjson.JSONObject;
import icu.fanjie.SpiderTracker;
import icu.fanjie.base.PrintStorage;

import java.io.FileWriter;
import java.io.IOException;

public class JSONStorage extends PrintStorage {
    private String path;

    public JSONStorage() {
        this.path = JSONStorage.class.getResource("/").getPath() + "/.result.json";
    }

    public JSONStorage(String path) {
        this.path = path;
    }

    @Override
    public void storage(SpiderTracker tracker) {
        Object parser = tracker.getExtraParams().get("parser");
        JSONObject jo = (JSONObject) parser;
        JSONObject parser_content = jo.getJSONObject("parser_content");
        if (parser_content != null) {
            writeFile(parser_content.toJSONString());
        }
    }

    public void writeFile(String content) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(this.path, true);
            writer.write(content);
            writer.write("\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
