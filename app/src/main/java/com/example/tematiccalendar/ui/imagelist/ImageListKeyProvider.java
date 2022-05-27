package com.example.tematiccalendar.ui.imagelist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.widget.RecyclerView;

public class ImageListKeyProvider extends ItemKeyProvider<Long> {

    public ImageListKeyProvider() {
        super(SCOPE_MAPPED);
    }

    @Nullable
    @Override
    public Long getKey(int position) {
        return (long) ImageResourceList.get(position);
    }

    @Override
    public int getPosition(@NonNull Long key) {
        return ImageResourceList.findPosition(key.intValue());
    }
}
