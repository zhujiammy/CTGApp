package com.c.ctgapp.mvvm.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.c.ctgapp.mvvm.model.AllianceList;
import com.c.ctgapp.mvvm.model.CompanyList;
import com.c.ctgapp.mvvm.model.PersonalInfo;
import com.c.ctgapp.mvvm.model.User;

@Database(entities = {User.class, PersonalInfo.class},version = 1,exportSchema = false)
@TypeConverters({AllianceList.class,CompanyList.class})
public abstract class AppDatabase extends RoomDatabase {
    abstract public  PersonalInfoDao personalInfoDao();
}
