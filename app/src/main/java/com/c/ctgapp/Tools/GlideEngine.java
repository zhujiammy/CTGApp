package com.c.ctgapp.Tools;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.listener.OnImageCompleteCallback;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;

public class GlideEngine implements ImageEngine {
    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {

    }

    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView, SubsamplingScaleImageView longImageView, OnImageCompleteCallback callback) {

    }

    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView, SubsamplingScaleImageView longImageView) {

    }

    @Override
    public void loadFolderImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {

    }

    @Override
    public void loadAsGifImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {

    }

    @Override
    public void loadGridImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {

    }
}
