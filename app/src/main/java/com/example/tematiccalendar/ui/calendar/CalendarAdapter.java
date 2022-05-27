package com.example.tematiccalendar.ui.calendar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tematiccalendar.R;
import com.example.tematiccalendar.db.DayImageEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    private List<DayImageEntity> days = Collections.emptyList();
    private final OnItemListener onItemListener;

    public CalendarAdapter(OnItemListener onItemListener)
    {
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.1666666);
        return new CalendarViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {
        DayImageEntity day = days.get(position);
        String dayOfMonth = "";
        if (day != null) {
            if (day.date != null) {
                dayOfMonth = String.valueOf(day.date.getDayOfMonth());
            }

            if (day.resourceId != null) {
                holder.dayOfMonth.setBackgroundResource(android.R.color.holo_blue_light);
            }
        }

        holder.dayOfMonth.setText(dayOfMonth);
    }

    @Override
    public int getItemCount()
    {
        return days.size();
    }

    public interface OnItemListener{
        void onItemClick(int position, String dayText);
    }

    public void setDays(List<DayImageEntity> days) {
        if (days != null) {
            this.days = days;
            notifyItemRangeChanged(0, days.size());
        }
    }

}
