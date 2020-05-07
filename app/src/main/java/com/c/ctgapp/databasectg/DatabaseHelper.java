package com.c.ctgapp.databasectg;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.c.ctgapp.Data.UserTablefields;
import com.c.ctgapp.mvvm.model.Response;
import com.c.ctgapp.mvvm.model.User;

import java.util.Objects;

//车同轨app数据库
public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context,String name,SQLiteDatabase.CursorFactory factory,int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表
        db.execSQL(UserTablefields.CREATE_PERSON);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }




}
