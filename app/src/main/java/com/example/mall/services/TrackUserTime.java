package com.example.mall.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.mall.Utils;
import com.example.mall.databasefiles.GroceryItem;

public class TrackUserTime extends Service {

    private int seconds = 0;
    private boolean shouldFinish = false;
    private GroceryItem item;
    private IBinder binder = new LocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        trackTime();
        return binder;
    }

    public class LocalBinder extends Binder {
        public TrackUserTime getService(){
            return TrackUserTime.this;
        }
    }

    private void trackTime(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!shouldFinish){
                    try {
                        Thread.sleep(1000);
                        seconds++;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thread.start();
    }

    public void setItem(GroceryItem item) {
        this.item = item;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        shouldFinish = true;
        int minutes = seconds / 60;
        if(minutes>0){
            if(null!=item) {
                Utils.changeUserPoint(this, item, minutes);
            }
        }
    }
}
