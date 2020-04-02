package com.c.ctgapp.Tools;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.c.ctgapp.Data.PhoneDto;
import com.c.ctgapp.Tools.AZList.AZItemEntity;

import java.util.ArrayList;
import java.util.List;

public class PhoneUtil {

    // 号码
    public final static String NUM = ContactsContract.CommonDataKinds.Phone.NUMBER;
    // 联系人姓名
    public final static String NAME = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
    //联系人头像
    public final static  String DISPLAY_NAME_PRIMARY = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY;

    //上下文对象
    private Context context;
    //联系人提供者的uri
    private Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

    public PhoneUtil(Context context){
        this.context = context;
    }

    //获取所有联系人
    public List<AZItemEntity> getPhone(){
        List<AZItemEntity> phoneDtos = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(phoneUri,new String[]{NUM,NAME,DISPLAY_NAME_PRIMARY},null,null,null);
        while (cursor.moveToNext()){
            AZItemEntity phoneDto = new AZItemEntity(cursor.getString(cursor.getColumnIndex(NAME)),cursor.getString(cursor.getColumnIndex(NUM)),getContactPhotoUrl(context, cursor.getString(cursor.getColumnIndex(DISPLAY_NAME_PRIMARY))));
            phoneDtos.add(phoneDto);
        }
        return phoneDtos;
    }


    /**
     * 根据名字获取联系人头像的 URL。
     * @param contactName 联系人的名字
     * @return 联系人头像的 URL
     */
    @Nullable
    public static Uri getContactPhotoUrl(Context context, String contactName) {
        Uri phoneUri = null;
        Cursor cursor = context.getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                new String[]{ContactsContract.Contacts.PHOTO_URI},
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " = ?",
                new String[]{contactName},
                null);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    String photoUrlString = cursor.getString(0);
                    if (!TextUtils.isEmpty(photoUrlString)) {
                        phoneUri = Uri.parse(photoUrlString);
                    }
                }
            } finally {
                cursor.close();
            }
        }
        return phoneUri;
    }
}
