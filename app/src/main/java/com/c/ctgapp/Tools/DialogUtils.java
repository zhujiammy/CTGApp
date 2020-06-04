package com.c.ctgapp.Tools;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.c.ctgapp.R;

public class DialogUtils {
    private AlertDialog.Builder builder;

    public void showTwo(Context context,String title,String msg) {
        builder = new AlertDialog.Builder(context).setIcon(R.mipmap.ic_launcher).setTitle(title)
                .setCancelable(false)
                .setMessage(msg).setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ToDo: 你想做的事情
                    }
                });
        builder.create().show();
    }
}
