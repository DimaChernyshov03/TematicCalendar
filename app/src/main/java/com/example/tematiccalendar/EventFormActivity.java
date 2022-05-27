package com.example.tematiccalendar;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.selection.Selection;
import androidx.recyclerview.selection.SelectionPredicates;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;

import com.example.tematiccalendar.databinding.ActivityEventFormBinding;
import com.example.tematiccalendar.db.AppDatabase;
import com.example.tematiccalendar.db.DayImageEntity;
import com.example.tematiccalendar.ui.imagelist.ImageListAdapter;
import com.example.tematiccalendar.ui.imagelist.ImageListDetailsLookup;
import com.example.tematiccalendar.ui.imagelist.ImageListKeyProvider;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;

public class EventFormActivity extends AppCompatActivity {

    private ActivityEventFormBinding activityBinding;
    private ImageListAdapter imageListAdapter;
    SelectionTracker<Long> selectionTracker;
    LocalDate date;
    AppDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = AppDatabase.getInstance(getApplicationContext());

        activityBinding = ActivityEventFormBinding.inflate(getLayoutInflater());
        imageListAdapter = new ImageListAdapter();
        activityBinding.imageList.setAdapter(imageListAdapter);

        setupImageListTracker();
        setupButtonHandlers();
        setContentView(activityBinding.getRoot());

        // получить дату, для которой выбирается картинка
        if (savedInstanceState == null) {
            // если Activity только что создана из Intent
            date = (LocalDate) getIntent().getSerializableExtra("date");
            fillForm();
        } else {
            // если Activity была перезапущена Android (например, изменение ориентации экрана)
            date = (LocalDate) savedInstanceState.getSerializable("date");
        }

    }

    private void fillForm() {
        DayImageEntity day = db.dayImageDao().findByDate(date);

        if (day != null) {
            activityBinding.editTextTitle.setText(day.text);
            selectionTracker.select(day.resourceId);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // сохранить данные, если происходит перезапуск Activity Android-ом
        outState.putSerializable("date", date);
    }

    // настройка SelectionTracker для списка картинок
    private void setupImageListTracker() {
        selectionTracker = new SelectionTracker.Builder<>(
                "image-selection",
                activityBinding.imageList,
                new ImageListKeyProvider(),
                new ImageListDetailsLookup(activityBinding.imageList),
                StorageStrategy.createLongStorage()
        ).withSelectionPredicate(SelectionPredicates.createSelectSingleAnything())
                .build();

        selectionTracker.addObserver(new SelectionTracker.SelectionObserver<Long>() {
            @Override
            public void onSelectionChanged() {
                super.onSelectionChanged();
            }

//            @Override
//            public void onSele ItemStateChanged(@NonNull Long key, boolean selected) {
//                super.onSelectionChanged(key, selected);
//            }
        });

        imageListAdapter.setSelectionTracker(selectionTracker);
    }

    private void setupButtonHandlers() {
        activityBinding.cancelButton.setOnClickListener(v -> finish());
        activityBinding.saveButton.setOnClickListener(v -> onSaveClick());

    }

    private void onSaveClick() {
        Selection<Long> selection = selectionTracker.getSelection();
        Long selected = selection.isEmpty() ? null : selection.iterator().next();
        String text = activityBinding.editTextTitle.getText().toString();

        if (text.isEmpty())  {
            showMessage("You must fill Title field");
        } else if (selected == null) {
            showMessage("You must select image");
        } else {
            DayImageEntity dayImageEntity = new DayImageEntity();
            dayImageEntity.date = date;
            dayImageEntity.text = text;
            dayImageEntity.resourceId = selected;

            db.dayImageDao().insert(dayImageEntity);

            finish();
        }
    }

    private void showMessage(String message) {
        Snackbar mySnackbar = Snackbar.make(activityBinding.getRoot(), message, BaseTransientBottomBar.LENGTH_SHORT);
        mySnackbar.show();
    }

}