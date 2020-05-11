package com.c.ctgapp.Tools;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.c.ctgapp.CTGApp;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.os.FileUtils.copy;

/**
 * 存储数据类
 */

public class Utils {
    public static final int YEAR = 0;

    public static final String CONFIG_FILE_NAME = "config";
    public static final int MOUNT = 1;

    public static final int DAY = 2;

    public static Toast mToast;

    public static void showToast(int msgId) {
        showToast(getString(msgId));
    }

    public static void showToast(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    public static void showToast(String msg, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(getContext(), "", duration);
        }
        mToast.setText(msg);
        mToast.show();
    }

    /**
     * 处理加载分辨率较大的图片
     */
    private Bitmap ReadBitmap(Context context, int resID) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;  //设置样式
        //加载资源文件
        InputStream stream = context.getResources().openRawResource(resID);
        return BitmapFactory.decodeStream(stream, null, opt);
    }

    public static void setShare(Context context, String key, int value) {
        SharedPreferences.Editor editor = context.getSharedPreferences("ShareData", Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getShared(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences("ShareData", Context.MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }


    public static String getString(int resId) {
        return getResource().getString(resId);
    }

    public static Resources getResource() {
        return getContext().getResources();
    }

    public static Context getContext() {
        return CTGApp.getBaseApplication();
    }

    public static boolean isMobileNO(String mobileNums) {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return 待检测的字符串
         */
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";// "[1]"代表下一位为数字可以是几，"[0-9]"代表可以为0-9中的一个，"[5,7,9]"表示可以是5,7,9中的任意一位,[^4]表示除4以外的任何一个,\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }

    public static boolean getShared3(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences("ShareData", Context.MODE_PRIVATE);
        return preferences.getBoolean(key, true);
    }

    public static void setShare2(Context context, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences("ShareData", Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void setShare5(Context context, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences("ShareData", Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void setShare3(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = context.getSharedPreferences("ShareData", Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }


    public static String getShared2(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences("ShareData", Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public <T> void getListShared(Context context, String key, List<T> values) {
        SharedPreferences.Editor editor = context.getSharedPreferences("ShareData", Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String strJson = gson.toJson(values);
        editor.putString(key, strJson);
        editor.commit();
    }

    public static <T> void setShare4(Context context, String key, T values) {
        SharedPreferences.Editor editor = context.getSharedPreferences("ShareData", Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String strJson = gson.toJson(values);
        editor.putString(key, strJson);
        editor.commit();
    }

    public static String getShared4(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences("ShareData", Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public static String getListShared(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences("ShareData", Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public static void ClearData(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("ShareData", Context.MODE_PRIVATE).edit();
        editor.clear().commit();
    }


    /**
     * 获取app的版本名称
     *
     * @param context
     * @return
     */

    public static String getAppVersionName(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取app的版本码
     *
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 获取当前时间
     */
    public static int getDateNow(int who) {
        SimpleDateFormat format = null;

        /**
         * 手机获取的时间装换成月份后是我们平常所见的月份
         * 但是 时间选择器中 月份是从0开始计数的
         */
        Date now = new Date(System.currentTimeMillis());
        switch (who) {
            case YEAR: {
                format = new SimpleDateFormat("yyyy");
                return Integer.parseInt(format.format(now));
            }
            case MOUNT: {
                format = new SimpleDateFormat("MM");
                return Integer.parseInt(format.format(now)) - 1;
            }
            case DAY: {
                format = new SimpleDateFormat("dd");
                return Integer.parseInt(format.format(now));
            }
            default:
                return 0;
        }
    }

    public static String getData() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        String data = formatter.format(curDate);
        return data;
    }

    /**
     * 获取银行卡后四位
     *
     * @param cardNum
     * @return
     */
    public static String getCardTailNum(String cardNum) {
        StringBuffer tailNum = new StringBuffer();
        if (cardNum != null) {
            int len = cardNum.length();
            for (int i = len - 1; i >= len - 4; i--) {
                tailNum.append(cardNum.charAt(i));
            }
            tailNum.reverse();
        }
        return tailNum.toString();
    }

    /*

     * 将时间戳转换为时间

     */

    public static String stampToDate(String s) {

        String res;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        long lt = new Long(s);

        Date date = new Date(lt);

        res = simpleDateFormat.format(date);

        return res;

    }

    /**
     * 获取当前的时间戳
     * @return
     */
    public static String getTimeStame() {
        //获取当前的毫秒值
        long time = System.currentTimeMillis();
        //将毫秒值转换为String类型数据
        String time_stamp = String.valueOf(time);
        //返回出去
        return time_stamp;
    }

    /**
     * 得到应用程序的包名
     *
     * @return
     */
    public static String getPackageName() {
        return getContext().getPackageName();
    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     */
    public static Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String listToString(List<String> mList) {
        String convertedListStr = "";
        if (null != mList && mList.size() > 0) {
            String[] mListArray = mList.toArray(new String[mList.size()]);
            for (int i = 0; i < mListArray.length; i++) {
                if (i < mListArray.length - 1) {
                    convertedListStr += mListArray[i] + ",";
                } else {
                    convertedListStr += mListArray[i];
                }
            }
            return convertedListStr;
        } else return "List is null!!!";
    }


    public static String getString(String key, String defValue) {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    public static void putString(String key, String value) {
        SharedPreferences sp = Utils.getContext().getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();


    }
}
