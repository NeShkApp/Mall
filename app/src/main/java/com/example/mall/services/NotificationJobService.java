package com.example.mall.services;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.mall.R;
import com.example.mall.activities.MainActivity;

@SuppressLint("SpecifyJobSchedulerIdRange")
public class NotificationJobService extends JobService {
    private static final String TAG = "NotificationJobService";
    private NotificationAsyncTask asyncTask;
    private JobParameters parameters;

    @Override
    public boolean onStartJob(JobParameters params) {
        parameters = params;
        asyncTask = new NotificationAsyncTask();
        asyncTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        if (null != asyncTask) {
            if (!asyncTask.isCancelled()) {
                asyncTask.cancel(true);
            }
        }
        Log.d(TAG, "onStopJob: Job Canceled!");
        return true;
    }

    @SuppressLint("StaticFieldLeak")
    private class NotificationAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            if (!isAppInForeground(getApplicationContext())) {
                sendNotification(getApplicationContext());
                Log.d(TAG, "doInBackground: Job Finished with notification");
                return "Job Finished with notification";
            } else {
                Log.d(TAG, "doInBackground: Job Finished without notification");
                return "Job Finished without notification";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            jobFinished(parameters, true);
        }
    }

    private void sendNotification(Context context) {

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 2, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel1")
                .setContentTitle("Hello")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentText("This is my first notification")
                .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
                .setColor(getResources().getColor(R.color.purple_200))
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.notify(1, builder.build());
    }

    private boolean isAppInForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            for (ActivityManager.RunningAppProcessInfo processInfo : activityManager.getRunningAppProcesses()) {
                if (processInfo.processName.equals(context.getPackageName()) && processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return true;
                }
            }
        }
        return false;
    }
}