package icu.fanjie.base;

import icu.fanjie.Dup;

import java.util.HashMap;
import java.util.HashSet;

public class BaseDup<E> implements Dup<E> {

    HashSet<Object> objects = new HashSet<>();

    public BaseDup() {

    }

    @Override
    public boolean isExist(Object o) {
        return false;
    }

    @Override
    public boolean add(Object o) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean clean() {
        return false;
    }
}
