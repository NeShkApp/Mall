package com.example.mall;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Build;

import com.example.mall.services.NotificationJobService;
import com.example.mall.services.SaleScheduler;

public class App extends Application {
    private static final String TAG = "App";
    private static final int NOTIFICATION_JOB_ID = 101;

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
        initNotificationJobScheduler();
        SaleScheduler.scheduleDailyWork();

    }

    private void initNotificationJobScheduler() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ComponentName componentName = new ComponentName(this, NotificationJobService.class);
            JobInfo.Builder builder = new JobInfo.Builder(NOTIFICATION_JOB_ID, componentName)
                    .setPersisted(true);
//            builder.setPeriodic(20*60*1000, 30*60*1000);
            builder.setPeriodic(15*60*1000, 10*60*1000);
            JobScheduler scheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
            scheduler.schedule(builder.build());
        }
    }


    private void createNotificationChannel() {
        NotificationChannel channel1 = new NotificationChannel("channel1", "Channel", NotificationManager.IMPORTANCE_HIGH);
        channel1.setDescription("This channel is using for main notifications");

        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel1);
    }

}
