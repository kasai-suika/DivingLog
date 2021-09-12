package com.ojtapp.divinglog.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.FileDescriptor;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ConversionUtil {
    /**
     * データがない場合の定数
     */
    public static final int NO_DATA = -1;

    ConversionUtil() {
    }

    /**
     * Uri型をBitmap型の画像に変換する。
     *
     * @param uri     Bitmap型に変換するUri
     * @param context コンテクスト
     * @return Uriから変換したBitmap型の画像
     * @throws IOException 例外
     */
    public static Bitmap getBitmapFromUri(@NonNull Uri uri, @NonNull Context context) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                context.getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    /**
     * String型をint型に変換する。
     * データがない場合は-1を返す。
     *
     * @param str 　String型のデータ
     * @return int型に変換したデータ
     */
    public static int getIntFromStr(@Nullable String str) {
        if (null == str) {
            return NO_DATA;
        } else {
            return Integer.parseInt(str);
        }
    }

    /**
     * int型をString型に変換する。
     * データがない場合は空文字を返す。
     *
     * @param intData 　int型のデータ
     * @return String型に変換したデータ
     */
    public static String getStrFromInt(int intData) {
        if (ConversionUtil.NO_DATA == intData) {
            return null;
        } else {
            return String.valueOf(intData);
        }
    }

    /**
     * 時間をString型のフォーマットに変換して返
     * @param timeFormat 時間のフォーマット
     * @param hour　時間
     * @param minute　分
     * @return Stringに変換後の時間
     */
    public static String getStrTime(@NonNull SimpleDateFormat timeFormat, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return timeFormat.format(calendar.getTime());
    }

    /**
     * 日付をString型のフォーマットに変換して返す
     * @param dateFormat 日付のフォーマット
     * @param year 年
     * @param month 月
     * @param day 日
     * @return Stringに変換後の日付
     */
    public static String getStrDate(@NonNull SimpleDateFormat dateFormat, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day); // 日付をカレンダークラスにセット
        return dateFormat.format(calendar.getTime());   // フォーマットを指定してDivingLogクラスにセット
    }

}
