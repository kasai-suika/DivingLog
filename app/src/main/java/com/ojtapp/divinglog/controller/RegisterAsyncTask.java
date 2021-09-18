package com.ojtapp.divinglog.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ojtapp.divinglog.constant.LogConstant;
import com.ojtapp.divinglog.appif.DivingLog;
import com.ojtapp.divinglog.model.OpenHelper;

import java.lang.ref.WeakReference;

/**
 * Log情報をDBに保存するクラス
 */
public class RegisterAsyncTask extends AsyncTask<DivingLog, Integer, Boolean> {
    /**
     * クラス名
     */
    private static final String TAG = RegisterAsyncTask.class.getSimpleName();
    /**
     * コンテクスト受け取り用
     */
    private final WeakReference<Context> weakReference;
    /**
     * コールバック受け取り用
     */
    private RegisterCallback registerCallback;

    /**
     * コンストラクタ
     * {@inheritDoc}
     */
    public RegisterAsyncTask(@NonNull Context context) {
        super();
        weakReference = new WeakReference<>(context);
    }

    /**
     * DB保存処理
     * <p>
     * {@inheritDoc}
     *
     * @return 保存成功：true　失敗：false
     */
    @Override
    protected Boolean doInBackground(DivingLog... divingLogs) {
        android.util.Log.d(TAG, "doInBackground");

        // 失敗
        if (divingLogs[0] == null) {
            return false;
        }

        //　非同期での処理
        // DB作成
        OpenHelper openHelper = new OpenHelper(weakReference.get());

        // DBを開く
        SQLiteDatabase db = openHelper.getWritableDatabase();

        // カラム名と保存するデータをセット
        ContentValues values = new ContentValues();
        values.put(LogConstant.DIVE_NUMBER, divingLogs[0].getDivingNumber());
        values.put(LogConstant.PLACE, divingLogs[0].getPlace());
        values.put(LogConstant.POINT, divingLogs[0].getPoint());
        values.put(LogConstant.DATE, divingLogs[0].getDate());
        values.put(LogConstant.TIME_START, divingLogs[0].getTimeStart());
        values.put(LogConstant.TIME_END, divingLogs[0].getTimeEnd());
        values.put(LogConstant.TIME_DIVE, divingLogs[0].getTimeDive());
        values.put(LogConstant.DEPTH_MAX, divingLogs[0].getDepthMax());
        values.put(LogConstant.DEPTH_AVE, divingLogs[0].getDepthAve());
        values.put(LogConstant.AIR_START, divingLogs[0].getAirStart());
        values.put(LogConstant.AIR_END, divingLogs[0].getAirEnd());
        values.put(LogConstant.AIR_DIVE, divingLogs[0].getAirDive());
        values.put(LogConstant.WEATHER, divingLogs[0].getWeather());
        values.put(LogConstant.TEMP, divingLogs[0].getTemp());
        values.put(LogConstant.TEMP_WATER, divingLogs[0].getTempWater());
        values.put(LogConstant.VISIBILITY, divingLogs[0].getVisibility());
        values.put(LogConstant.MEMBER, divingLogs[0].getMember());
        values.put(LogConstant.MEMBER_NAVIGATE, divingLogs[0].getMemberNavigate());
        values.put(LogConstant.MEMO, divingLogs[0].getMemo());
        values.put(LogConstant.PICTURE, divingLogs[0].getPictureUri());

        // 保存実行
        db.insert(LogConstant.TABLE_NAME, null, values);
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        // 進捗状況の表示などの処理
    }

    /**
     * 非同期処理終了時にコールバックを呼び出す
     * {@inheritDoc}
     */
    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (null != registerCallback) {
            if (result) {
                registerCallback.onSuccess();
            } else {
                registerCallback.onFailure();
            }
        }
    }

    /**
     * コールバック処理を設定
     *
     * @param registerCallback 　コールバックする内容
     */
    public void setOnCallBack(@Nullable RegisterCallback registerCallback) {
        this.registerCallback = registerCallback;
    }

    /**
     * コールバック用インターフェイス
     */
    public interface RegisterCallback {
        void onSuccess();

        void onFailure();
    }
}
