package com.example.tematiccalendar.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.widget.Toast;

import com.example.tematiccalendar.db.AppDatabase;
import com.example.tematiccalendar.db.DayImageEntity;

import java.time.LocalDate;
import java.time.YearMonth;

public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        AppDatabase db = AppDatabase.getInstance(context.getApplicationContext());

        DayImageEntity day = db.dayImageDao().findByDate(LocalDate.now());
        if (day != null) {
            NotificationManager manager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            Notification.Builder builder = new Notification.Builder(context, "CALENDAR");
            if (day.resourceId != null) {
                builder.setSmallIcon(day.resourceId.intValue());
                builder.setLargeIcon(Icon.createWithResource(context, day.resourceId.intValue()));
            }
            if (day.text != null) {
                builder.setContentTitle(day.text);
            }
            manager.notify(0, builder.build());
        }

    }
}
