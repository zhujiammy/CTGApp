package com.c.ctgapp.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.c.ctgapp.mvvm.model.User;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {
    @Insert(onConflict = REPLACE)
    long insertUsers(User user);
    @Query("SELECT * FROM user WHERE userId == :userId")
    Cursor loadAllUsersByUserid(String userId);
}
