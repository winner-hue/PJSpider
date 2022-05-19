package icu.fanjie;

import java.util.List;

public interface Dup{
    boolean isExist(Object o);

    boolean add(Object o);

    boolean remove(Object o);

    boolean clean();

    List<SpiderTracker> dup(SpiderTracker spiderTracker);

    void destroy();
}
