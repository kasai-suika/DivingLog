package com.ojtapp.divinglog.BindingAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;

import com.ojtapp.divinglog.util.ConversionUtil;

import java.io.IOException;

public class ImageViewBindingAdapter extends BaseObservable {

    @BindingAdapter(value = {"app:imageUri", "app:imageContext"})
    public static void setImageView(ImageView imageView, Uri uri, Context context) {
        if (null != uri) {
            try {
                Bitmap bmp = ConversionUtil.getBitmapFromUri(uri, context);
                imageView.setImageBitmap(bmp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
