package com.example.mall;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
        // TODO: 4/4/2024 Start notification service
//        startService(new Intent(this, NotificationService.class));
    }


    private void createNotificationChannel() {
        NotificationChannel channel1 = new NotificationChannel("channel1", "Channel", NotificationManager.IMPORTANCE_HIGH);
        channel1.setDescription("This channel is using for main notifications");

        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel1);
    }

}
