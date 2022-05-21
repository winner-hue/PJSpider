package icu.fanjie;

import icu.fanjie.base.*;
import icu.fanjie.base.plugin.RedisDup;
import icu.fanjie.base.plugin.RedisQueue;

import java.io.Serializable;
import java.sql.SQLException;

public class TestDemo implements Serializable {
    public static void main(String[] args) throws SQLException {
        // 创建调度器
        BaseSpiderScheduler scheduler = new BaseSpiderScheduler();
        // 创建任务
        SpiderTracker spiderTracker = new SpiderTracker();
        // 创建下载器
        Downloader downloader = new BaseDownloader();
        // 创建解析器
        Parser parser = new BaseNewsParser();
        // 创建存储器
        Storage storage = new PrintStorage();

        // 创建排重库
        Dup dup = new RedisDup();

        spiderTracker.setDownloader(downloader);
        spiderTracker.setParser(parser);
        // 设置起始种子爬虫
        spiderTracker.setSeed("https://blog.csdn.net/nav/back-end");
        // 设置种子优先级
        spiderTracker.setPriority(1);
        // 设置种子类型
        spiderTracker.getExtraParams().put("parser_type", "list");
        // 添加任务
        Queue queue = new RedisQueue();
        queue.add(spiderTracker);
        scheduler.setSpiderTrackers(queue);
        // 添加任务名
        scheduler.setTaskName("csdn");
        scheduler.setStorage(storage);
        scheduler.setDupQueue(dup);
        scheduler.start();
//        RedisQueue queue = new RedisQueue();
//        boolean add = queue.add(spiderTracker);
//        SpiderTracker test = (SpiderTracker) queue.get();
//        System.out.println(test.downloader);

    }
}
