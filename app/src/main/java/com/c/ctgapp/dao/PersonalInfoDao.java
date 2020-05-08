package com.c.ctgapp.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.c.ctgapp.mvvm.model.PersonalInfo;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface PersonalInfoDao {
    @Insert(onConflict = REPLACE)
    long insertPersonal(PersonalInfo personalInfo);
    @Query("SELECT * FROM personalinfo WHERE realname == :realname")
    PersonalInfo getpersonalinfo(String realname);

}
