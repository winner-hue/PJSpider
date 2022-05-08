package icu.fanjie;

public interface Queue<E> {
    boolean add(E e) throws InterruptedException;

    E remove();

    boolean remove(E e);

    int size();

    boolean isEmpty();

    E get();
}
