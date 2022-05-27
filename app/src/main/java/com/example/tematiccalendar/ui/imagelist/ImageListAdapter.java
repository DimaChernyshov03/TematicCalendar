package com.example.tematiccalendar.ui.imagelist;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tematiccalendar.R;
import com.example.tematiccalendar.databinding.ImageItemBinding;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageViewHolder>{

    private SelectionTracker<Long> selectionTracker;

    public void setSelectionTracker(SelectionTracker<Long> selectionTracker) {
        this.selectionTracker = selectionTracker;
    }

    @NonNull
    @Override
    public ImageListAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageItemBinding imageItemBinding = DataBindingUtil.inflate(LayoutInflater.from(
                parent.getContext()),
                R.layout.image_item,
                parent, false
        );
        return new ImageViewHolder(imageItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.bind(ImageResourceList.get(position));
    }

    @Override
    public int getItemCount() {
        return ImageResourceList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        private ImageItemBinding imageItemBinding;
        private int resourceId;

        public ImageViewHolder(@NonNull ImageItemBinding imageItemBinding) {
            super(imageItemBinding.getRoot());
            this.imageItemBinding = imageItemBinding;
        }

        public void bind(int resourceId) {
            imageItemBinding.imageView.setImageResource(resourceId);
            imageItemBinding.imageView.setActivated(selectionTracker.isSelected((long) resourceId));
            this.resourceId = resourceId;
        }

        public ItemDetailsLookup.ItemDetails<Long> getItemDetails() {
            return new ItemDetailsLookup.ItemDetails<Long>() {

                @Override
                public int getPosition() {
                    return getAbsoluteAdapterPosition();
                }

                @Nullable
                @Override
                public Long getSelectionKey() {
                    return (long) resourceId;
                }
            };
        }
    }
}
