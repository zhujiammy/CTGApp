package com.c.ctgapp.mvvm.db;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.c.ctgapp.mvvm.model.PersonalInfo;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface PersonalInfoDao {
    @Insert(onConflict = REPLACE)
    long insertPersonal(PersonalInfo personalInfo);
    @Query("SELECT * FROM personalinfo WHERE realname == :realname")
    LiveData<PersonalInfo> getpersonalinfo(String realname);
    @Query("UPDATE personalinfo SET file= :file ,nickname=:nickname,work=:work,edulevel= :edulevel WHERE realname == :realname")
    int  getupdatepersonalinfo(String realname,String file,String nickname,String work,String edulevel);

}
