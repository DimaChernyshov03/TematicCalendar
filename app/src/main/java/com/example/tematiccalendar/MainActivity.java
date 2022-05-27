package com.example.tematiccalendar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.tematiccalendar.ui.calendar.CalendarAdapter;
import com.example.tematiccalendar.viewmodel.CalendarViewModel;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;
    private CalendarViewModel daysModelView;
    private ActivityResultLauncher<LocalDate> formLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initwidgets();
        selectedDate = LocalDate.now();
        createMonthView();

        formLauncher = registerForActivityResult(new EventActivityResultContract(), result -> {
            updateCalendar();
        });
    }

    private void initwidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    private void createMonthView() {
        daysModelView = new ViewModelProvider(this).get(CalendarViewModel.class);



        monthYearText.setText(monthYearFromDate(selectedDate));
//        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);


        daysModelView.getDayImages().observe(this, dayImageEntities -> {
            if (dayImageEntities != null) {
                calendarAdapter.setDays(dayImageEntities);
            }
        });

    }


    private String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public void previousMonthAction(View view) {
        selectedDate = selectedDate.minusMonths(1);
        updateCalendar();
    }

    public void nextMonthAction(View view) {
        selectedDate = selectedDate.plusMonths(1);
        updateCalendar();
    }

    public void updateCalendar() {
        daysModelView.setYearMonth(YearMonth.from(selectedDate));
        monthYearText.setText(monthYearFromDate(selectedDate));

    }

    @Override
    public void onItemClick(int position, String dayText) {
        if (!dayText.equals("")) {
            LocalDate selectedDay = LocalDate.from(selectedDate);
            selectedDay = selectedDay.withDayOfMonth(Integer.parseInt(dayText));
            formLauncher.launch(selectedDay);
        }
    }

    public class EventActivityResultContract extends ActivityResultContract<LocalDate, Boolean> {

        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, LocalDate date) {
            Intent intent = new Intent(context, EventFormActivity.class);
            intent.putExtra("date", date);

            return intent;
        }

        @Override
        public Boolean parseResult(int resultCode, @Nullable Intent intent) {
            return true;
        }
    }

}