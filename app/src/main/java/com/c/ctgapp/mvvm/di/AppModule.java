package com.c.ctgapp.mvvm.di;

import android.app.Application;
import androidx.room.Room;

import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioManager;

import com.c.ctgapp.Tools.AppExecutors;
import com.c.ctgapp.mvvm.db.AppDatabase;
import com.c.ctgapp.mvvm.db.PersonalInfoDao;
import com.c.ctgapp.retrofit.Serviece;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Singleton @Provides
    Application providesApplication() {
        return application;
    }


    @Singleton @Provides
    AppDatabase provideDb(Application app) {
        return Room.databaseBuilder(app, AppDatabase.class,"ctg_db.db").build();
    }

    @Singleton @Provides
    PersonalInfoDao personalInfoDao(AppDatabase db) {
        return db.personalInfoDao();
    }
    @Singleton
    @Provides
    AppExecutors appExecutors (){
        return new AppExecutors();
    }


}