package com.example.tematiccalendar;

import android.app.AlarmManager;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.SystemClock;

import com.example.tematiccalendar.db.AppDatabase;
import com.example.tematiccalendar.receiver.AlarmReceiver;

public class CalendarApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
        createNotificationAlarm();


    }

    private void createNotificationChannel() {
        NotificationManager manager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel channel = new NotificationChannel("CALENDAR",
                "Calendar notifications", NotificationManager.IMPORTANCE_DEFAULT);

        manager.createNotificationChannel(channel);
    }

    private void createNotificationAlarm() {
        AlarmManager alarmMgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        // можно включить для отладки, чтобы уведомление было каждое 10 секунд
        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                10 * 1000, 10 * 1000, alarmIntent);

        // уведомления каждые день в 12:00
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, 12);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
//                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);

    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }
}
