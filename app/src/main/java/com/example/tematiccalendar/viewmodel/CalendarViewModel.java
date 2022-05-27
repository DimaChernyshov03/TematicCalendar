package com.example.tematiccalendar.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;

import com.example.tematiccalendar.CalendarApp;
import com.example.tematiccalendar.db.AppDatabase;
import com.example.tematiccalendar.db.DayImageEntity;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CalendarViewModel extends AndroidViewModel {
    private final LiveData<List<DayImageEntity>> dayImages;
    private final SavedStateHandle savedStateHandle;
    private final AppDatabase database;

    private final static String DATE_KEY = "DATE";

    public CalendarViewModel(@NonNull Application app, @NonNull SavedStateHandle savedStateHandle) {
        super(app);
        this.savedStateHandle = savedStateHandle;
        database = ((CalendarApp) app).getDatabase();

        dayImages = Transformations.map(
                savedStateHandle.getLiveData(DATE_KEY, YearMonth.now()),
                (Function<YearMonth, List<DayImageEntity>>) this::getDaysForMonth
        );
    }

    public LiveData<List<DayImageEntity>> getDayImages() {
        return dayImages;
    }

    public void setYearMonth(YearMonth yearMonth) {
        savedStateHandle.set(DATE_KEY, yearMonth);
    }

    private List<DayImageEntity> getDaysForMonth(YearMonth yearMonth) {
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        List<DayImageEntity> dbDays = database.dayImageDao().findByDateRange(startDate, endDate);
        Map<LocalDate, DayImageEntity> dbDaysSet = new HashMap<>();
        for (DayImageEntity day : dbDays) {
            dbDaysSet.put(day.date, day);
        }

        int daysInMonth = yearMonth.lengthOfMonth();
        int dayOfWeek = startDate.getDayOfWeek().getValue();


        List<DayImageEntity> resultDays = new ArrayList<>();

        // пустые даты в начале календаря
        for (int i = 1; i < dayOfWeek; i++) {
            resultDays.add(null);
        }

        // дни текущего месяца
        for (int i = 1; i <= endDate.getDayOfMonth(); i++) {
            LocalDate date = yearMonth.atDay(i);
            DayImageEntity day = dbDaysSet.get(date);
            if (day == null) {
                day = new DayImageEntity();
                day.date = date;

            }
            resultDays.add(day);
        }

        // пустые дни в конце календаря
        for (int i = dayOfWeek + daysInMonth; i <= 42; i++) {
            resultDays.add(null);
        }

        return resultDays;
    }
}
