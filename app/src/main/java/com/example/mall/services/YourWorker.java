package com.example.mall.services;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.mall.Utils;
import com.example.mall.databasefiles.GroceryItem;
import com.example.mall.databasefiles.ShopDatabase;

import java.util.List;

public class YourWorker extends Worker {
    private static final String TAG = "YourWorker";

    private ShopDatabase shopDatabase;

    public YourWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        shopDatabase = ShopDatabase.getInstance(context);
    }

    @NonNull
    @Override
    public Result doWork() {
        Utils.cancelSales(getApplicationContext());
        Utils.addSales(getApplicationContext());
        return Result.success();
    }
}

