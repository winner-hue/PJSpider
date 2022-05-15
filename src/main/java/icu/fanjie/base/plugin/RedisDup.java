package icu.fanjie.base.plugin;

import icu.fanjie.SpiderTracker;
import icu.fanjie.base.BaseDup;

import java.util.List;

public class RedisDup<E> extends BaseDup<E> {
    protected String host = "127.0.0.1";
    protected int port = 6379;
    protected int db = 0;
    protected String password = "root";

    public RedisDup() {
    }

    @Override
    public boolean isExist(Object o) {
        return super.isExist(o);
    }

    @Override
    public boolean add(Object o) {
        return super.add(o);
    }

    @Override
    public boolean remove(Object o) {
        return super.remove(o);
    }

    @Override
    public boolean clean() {
        return super.clean();
    }

    @Override
    public List<SpiderTracker> dup(SpiderTracker spiderTracker) {
        return super.dup(spiderTracker);
    }

}
