package com.example.tematiccalendar.ui.imagelist;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

public class ImageListDetailsLookup extends ItemDetailsLookup<Long> {

    private final RecyclerView recyclerView;

    public ImageListDetailsLookup(@NonNull RecyclerView recyclerView) {
        super();
        this.recyclerView = recyclerView;
    }

    @Nullable
    @Override
    public ItemDetails<Long> getItemDetails(@NonNull MotionEvent e) {
        View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
        if (view != null) {
            RecyclerView.ViewHolder viewHolder = recyclerView.findContainingViewHolder(view);
            if (viewHolder instanceof ImageListAdapter.ImageViewHolder) {
                return ((ImageListAdapter.ImageViewHolder) viewHolder).getItemDetails();
            }
        }
        return null;
    }
}
