package com.bjhit.video.common;

import static java.lang.Integer.MIN_VALUE;

import java.util.Arrays;

/**
 * 
 * @description
 * @project bjhit-video
 * @author guanxianchun
 * @Create 2015-2-11 下午5:05:45
 * @version 1.0
 */
public class IntIntMap {

    private static final int GROW_BY = 128;
    private int[] storage;
    private int size;

    public IntIntMap() {
        this.storage = createArray(GROW_BY);
        Arrays.fill(this.storage, MIN_VALUE);
    }

    public void put(int key, int val) {
        if (val == MIN_VALUE)
            throw new IllegalArgumentException("This implementation can not store " + MIN_VALUE);
        
        if (storage.length <= key) {
            int[] ns = createArray(key + GROW_BY);
            System.arraycopy(storage, 0, ns, 0, storage.length);
            Arrays.fill(ns, storage.length, ns.length, MIN_VALUE);
            storage = ns;
        }
        if (storage[key] == MIN_VALUE)
            size++;
        storage[key] = val;
    }

    public int get(int key) {
        return key >= storage.length ? null : storage[key];
    }

    public int[] keys() {
        int[] result = new int[size];
        for (int i = 0, r = 0; i < storage.length; i++) {
            if (storage[i] != MIN_VALUE)
                result[r++] = i;
        }
        return result;
    }

    public void clear() {
        for (int i = 0; i < storage.length; i++)
            storage[i] = MIN_VALUE;
        size = 0;
    }

    public int size() {
        return size;
    }

    public void remove(int key) {
        if (storage[key] != Integer.MIN_VALUE)
            size--;
        storage[key] = MIN_VALUE;
    }

    public int[] values() {
        int[] result = createArray(size);
        for (int i = 0, r = 0; i < storage.length; i++) {
            if (storage[i] != MIN_VALUE)
                result[r++] = storage[i];
        }
        return result;
    }

    private int[] createArray(int size) {
        return new int[size];
    }
}
