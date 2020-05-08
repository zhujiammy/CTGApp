package com.c.ctgapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;
import androidx.room.migration.Migration;

import com.c.ctgapp.databasectg.AppDatabase;
import com.facebook.stetho.Stetho;
import com.pilot.common.base.application.BaseApplication;


public class CTGApp extends BaseApplication {


    private AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        appDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "ctg_db.db").build();
    }

    public AppDatabase getAppDatabase(){
        return appDatabase;
    }
    public void closedatabase(){
        if(appDatabase.isOpen()){
            appDatabase.close();
        }
    }

    @Override
    protected void initializeApplication() {

    }

    @Override
    protected void deInitializeApplication() {

    }

    @Override
    protected void onAppCrash(Context context, Thread thread, Throwable ex) {

    }

    @Override
    public void onActivityPreCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityPostCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityPreStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPostStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPreResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPostResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPrePaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPostPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPreStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPostStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPreSaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityPostSaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityPreDestroyed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPostDestroyed(@NonNull Activity activity) {

    }
}
