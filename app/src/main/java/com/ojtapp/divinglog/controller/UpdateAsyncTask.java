package com.ojtapp.divinglog.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ojtapp.divinglog.constant.LogConstant;
import com.ojtapp.divinglog.appif.DivingLog;
import com.ojtapp.divinglog.model.OpenHelper;

import java.lang.ref.WeakReference;

/**
 * 【DB】データ更新処理クラス
 */
public class UpdateAsyncTask extends AsyncTask<DivingLog, Integer, Boolean> {
    /**
     * クラス名
     */
    private static final String TAG = UpdateAsyncTask.class.getSimpleName();
    /**
     * Context受け取り用
     */
    @NonNull
    private final WeakReference<Context> weakReference;
    /**
     * コールバック設定用
     */
    @Nullable
    private UpdateCallback updateCallback;

    public UpdateAsyncTask(@NonNull Context context) {
        super();
        weakReference = new WeakReference<>(context);
    }

    /**
     * DB更新処理
     *
     * @param divingLogs 　ダイビングログ
     * @return 更新処理が成功：true
     */
    @Override
    protected Boolean doInBackground(DivingLog... divingLogs) {
        Log.d(TAG, "doInBackground");

        // 失敗
        if (null == divingLogs[0]) {
            return false;
        }

        // データベースを開く
        OpenHelper openHelper = new OpenHelper(weakReference.get());
        SQLiteDatabase db = openHelper.getWritableDatabase();

        // カラム名と保存するデータを設定
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

        // 更新処理
        String selection = LogConstant.LOG_ID + " = ?";
        String[] selectionArgs = {String.valueOf(divingLogs[0].getLogId())};

        db.update(
                LogConstant.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
    }

    /**
     * 非同期処理終了時にコールバックを呼び出す
     * {@inheritDoc}
     */
    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (null != updateCallback) {
            if (result) {
                updateCallback.onSuccess();
            } else {
                updateCallback.onFailure();
            }
        }
    }

    /**
     * コールバック処理を設定
     *
     * @param updateCallback コールバックする内容
     */
    public void setUpdateCallback(@Nullable UpdateCallback updateCallback) {
        this.updateCallback = updateCallback;
    }

    /**
     * コールバック用インターフェイス
     */
    public interface UpdateCallback {
        void onSuccess();

        void onFailure();
    }
}
