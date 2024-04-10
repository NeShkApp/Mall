//package com.example.mall;
//
//import android.Manifest;
//import android.app.Notification;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.os.Handler;
//import android.os.IBinder;
//import android.util.Log;
//
//import androidx.core.app.ActivityCompat;
//import androidx.core.app.NotificationCompat;
//import androidx.core.app.NotificationManagerCompat;
//
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class NotificationService extends Service {
//    private static final String TAG = "Timers";
//
//    Timer timer;
//    TimerTask timerTask;
//
//    int Your_X_SECS = 5;
//
//
//    @Override
//    public IBinder onBind(Intent arg0) {
//        return null;
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.e(TAG, "onStartCommand");
//        super.onStartCommand(intent, flags, startId);
//
////        startTimer();
//
//        return START_STICKY;
//    }
//
//
//    @Override
//    public void onCreate() {
//        Log.e(TAG, "Service: onCreate: Notification Service stopped successfully");
//        stoptimertask();
//
//    }
//
//    @Override
//    public void onDestroy() {
//        Log.e(TAG, "Service: onDestroy: Notification Service created successfully");
////        stoptimertask();
//        startTimer();
//        super.onDestroy();
//
//
//    }
//
//    //we are going to use a handler to be able to run in our TimerTask
//    final Handler handler = new Handler();
//
//
//    public void startTimer() {
//        //set a new Timer
//        timer = new Timer();
//
//        //initialize the TimerTask's job
//        initializeTimerTask();
//
//        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
////        timer.schedule(timerTask, 1000, Your_X_SECS * 1000);
//        timer.schedule(timerTask, 1000,Your_X_SECS * 1000);
//    }
//
//    public void stoptimertask() {
//        //stop the timer, if it's not already null
//        if (timer != null) {
//            timer.cancel();
//            timer = null;
//        }
//    }
//
//    public void initializeTimerTask() {
//
//        timerTask = new TimerTask() {
//            public void run() {
//
//                //use a handler to run a toast that shows the current timestamp
//                handler.post(new Runnable() {
//                    public void run() {
//
//                        sendNotification(getBaseContext());
//
//                    }
//                });
//            }
//        };
//    }
//
//    private void sendNotification(Context context) {
//
//        Intent intent = new Intent(context, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 2, intent, 0);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel1")
//                .setContentTitle("Hello")
//                .setSmallIcon(R.drawable.ic_bell)
//                .setContentText("This is my first notification")
//                .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
//                .setColor(getResources().getColor(R.color.purple_200))
//                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true);
//        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        manager.notify(1, builder.build());
//    }
//}