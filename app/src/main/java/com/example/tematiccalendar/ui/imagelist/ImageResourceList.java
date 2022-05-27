package com.example.tematiccalendar.ui.imagelist;

import com.example.tematiccalendar.R;

import java.util.ArrayList;
import java.util.List;

public class ImageResourceList {
    private final static List<Integer> imageIds = new ArrayList<>();
    static {
        imageIds.add(R.mipmap.ic_backlajan);
        imageIds.add(R.mipmap.ic_ogurec);
        imageIds.add(R.mipmap.ic_pomidor);
    }

    public static int get(int i) {
        return imageIds.get(i);
    }

    public static int size() {
        return imageIds.size();
    }

    public static int findPosition(int value) {
        return imageIds.indexOf(value);
    }
}
