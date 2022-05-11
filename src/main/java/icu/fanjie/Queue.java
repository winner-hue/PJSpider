package icu.fanjie;

public interface Queue {
    boolean add(Object e);

    Object remove();

    boolean remove(Object e);

    int size();

    boolean isEmpty();

    Object get();
}
