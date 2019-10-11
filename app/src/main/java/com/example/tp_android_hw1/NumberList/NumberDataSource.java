package com.example.tp_android_hw1.NumberList;

import java.util.ArrayList;
import java.util.List;

public class NumberDataSource {
    private final List<Integer> mData;
    private static NumberDataSource sInstance;

    private NumberDataSource() {
        mData = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            mData.add(i);
        }
    }

    public List<Integer> getData() {
        return mData;
    }

    public synchronized static NumberDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new NumberDataSource();
        }
        return sInstance;
    }
}
