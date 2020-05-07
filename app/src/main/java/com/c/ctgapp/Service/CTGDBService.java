package com.c.ctgapp.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.c.ctgapp.Data.UserTablefields;
import com.c.ctgapp.dao.CTGDao;
import com.c.ctgapp.databasectg.DatabaseHelper;
import com.c.ctgapp.mvvm.model.Response;
import com.c.ctgapp.mvvm.model.User;

import java.util.Objects;

public class CTGDBService implements CTGDao {

    private DatabaseHelper db = null;

    public CTGDBService(Context context,String name,SQLiteDatabase.CursorFactory factory,int version )
    {
        db = new DatabaseHelper(context,name,factory,version);
    }



    @Override
    public boolean addUserData(Response<User.UserBean> responseMutableLiveData) {
        //添加到user表数据
        //把数据添加到ContentValues
        ContentValues values = new ContentValues();
        values.put(UserTablefields.USERID, Objects.requireNonNull(responseMutableLiveData).getData().getUserId());
        values.put(UserTablefields.Telephone,responseMutableLiveData.getData().getTelephone());
        values.put(UserTablefields.RealName,responseMutableLiveData.getData().getRealName());
        values.put(UserTablefields.NickName,responseMutableLiveData.getData().getNickName());
        values.put(UserTablefields.Sex,responseMutableLiveData.getData().getSex());
        values.put(UserTablefields.Status,responseMutableLiveData.getData().getStatus());
        values.put(UserTablefields.Token,responseMutableLiveData.getData().getToken());
        values.put(UserTablefields.CtgId,responseMutableLiveData.getData().getCtgId());
        values.put(UserTablefields.Birth,responseMutableLiveData.getData().getBirth());
        values.put(UserTablefields.File,responseMutableLiveData.getData().getFile());
        values.put(UserTablefields.Type,responseMutableLiveData.getData().getType());
        values.put(UserTablefields.Industry,responseMutableLiveData.getData().getIndustry());
        values.put(UserTablefields.LoginCompanyIndustry,responseMutableLiveData.getData().getLoginCompanyIndustry());
        values.put(UserTablefields.DefaultCompanyId,responseMutableLiveData.getData().getDefaultCompanyId());
        values.put(UserTablefields.DefaultCompanyName,responseMutableLiveData.getData().getDefaultCompanyName());
        values.put(UserTablefields.CompanyList, String.valueOf(responseMutableLiveData.getData().getCompanyList()));
        values.put(UserTablefields.AllianceId,responseMutableLiveData.getData().getAllianceId());
        values.put(UserTablefields.AllianceName,responseMutableLiveData.getData().getAllianceName());
        values.put(UserTablefields.AllianceRole,responseMutableLiveData.getData().getAllianceRole());
        values.put(UserTablefields.CompanyId,responseMutableLiveData.getData().getCompanyId());
        values.put(UserTablefields.CompanyName,responseMutableLiveData.getData().getCompanyName());
        values.put(UserTablefields.Position,responseMutableLiveData.getData().getPosition());
        values.put(UserTablefields.Expert,responseMutableLiveData.getData().getExpert());
        values.put(UserTablefields.AllianceList, String.valueOf(responseMutableLiveData.getData().getAllianceList()));
        values.put(UserTablefields.BelongStatus,responseMutableLiveData.getData().getBelongStatus());
        //添加数据到数据库
        long index = db.getWritableDatabase().insert(UserTablefields.TABLE_NAME_PERSON,null,values);
        //大于0表示添加成功
        if(index > 0)
        {
            return true;
        }else
        {
            return false;
        }

    }

    @Override
    public void queryUserData(Response<User.UserBean> responseMutableLiveData) {
        //查询userid = 登入用户的id数据
        Cursor cursor = db.getWritableDatabase().query(UserTablefields.TABLE_NAME_PERSON,null,UserTablefields.USERID+"=?",new String[]{String.valueOf(responseMutableLiveData.getData().getUserId())},null,null,null);
        if(cursor.getCount() == 0){
            if(addUserData(responseMutableLiveData)){
                Log.e("TAG", "onCreate: "+"数据成功插入user表" );
            }else {
                Log.e("TAG", "onCreate: "+"数据插入失败" );
            }
        }else {
            Log.e("TAG", "onCreate: "+"数据已存在" );
        }
        cursor.close();
        db.getWritableDatabase().close();
    }




}
