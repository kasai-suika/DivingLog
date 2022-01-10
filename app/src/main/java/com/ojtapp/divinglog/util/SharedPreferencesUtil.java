package com.ojtapp.divinglog.util;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class SharedPreferencesUtil {
    /**
     * ソートモードを記憶するファイル名
     */
    public static final String FILE_NAME_SORT = "SORT_DATA";
    /**
     * ソートモードのポジション（デフォルト）
     */
    public static final int SORT_MODE_DEFAULT_POSITION = 0;
    /**
     * ソートモードのキー
     */
    public static final String KEY_SORT_MODE = "KEY_SORT_MODE";

    public SharedPreferencesUtil() {
    }

    /**
     * int型の情報を記憶する
     *
     * @param key   　キー
     * @param value 　記憶する値
     */
    public static void setInt(@NonNull String key, int value, SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * int型の情報を取得する
     *
     * @param key 　キー
     * @return keyに保存されていた値
     */
    public static int getSortMode(String key, SharedPreferences sharedPreferences) {
        return sharedPreferences.getInt(key, SORT_MODE_DEFAULT_POSITION);
    }
}
