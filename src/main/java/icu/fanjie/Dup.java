package icu.fanjie;

public interface Dup<E> {
    boolean isExist(Object o);

    boolean add(Object o);

    boolean remove(Object o);

    boolean clean();

}
