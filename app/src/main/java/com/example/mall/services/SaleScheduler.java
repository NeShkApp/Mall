package com.example.mall.services;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.mall.services.YourWorker;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class SaleScheduler {

    public static void scheduleDailyWork() {
        // Перевіряємо, чи вже запланована робота на сьогодні
        if (!isWorkScheduledToday()) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 1);

            // Отримайте поточний час
            long currentTimeMillis = System.currentTimeMillis();

            // Якщо час вже минув сьогодні, перенесіть його на завтра
//            if (calendar.getTimeInMillis() <= currentTimeMillis) {
//                calendar.add(Calendar.DAY_OF_MONTH, 1);
//            }

            PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(YourWorker.class, 1, TimeUnit.DAYS)
                    .setInitialDelay(calendar.getTimeInMillis() - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .build();

            // Заплануйте щоденне повторення завдання
            WorkManager.getInstance().enqueueUniquePeriodicWork("daily_work", ExistingPeriodicWorkPolicy.REPLACE, workRequest);
        }
    }

    private static boolean isWorkScheduledToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 1);

        // Отримайте список запланованих робіт
        WorkManager workManager = WorkManager.getInstance();
        ListenableFuture<List<WorkInfo>> statuses = workManager.getWorkInfosForUniqueWork("daily_work");

        try {
            // Отримайте результат майбутньої роботи
            List<WorkInfo> workInfos = statuses.get();

            // Перевірте, чи є запланована робота на сьогодні
            for (WorkInfo workInfo : workInfos) {
                if (workInfo.getState() == WorkInfo.State.ENQUEUED) {
                    // Якщо робота вже запланована на сьогодні, поверніть true
                    return true;
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return false;
    }

}