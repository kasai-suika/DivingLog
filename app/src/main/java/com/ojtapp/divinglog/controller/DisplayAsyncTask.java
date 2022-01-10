package com.ojtapp.divinglog.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ojtapp.divinglog.constant.LogConstant;
import com.ojtapp.divinglog.appif.DivingLog;
import com.ojtapp.divinglog.model.OpenHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Log情報をDBから取得するクラス
 */
public class DisplayAsyncTask extends AsyncTask<Integer, Integer, List<DivingLog>> {
    /**
     * クラス名
     */
    private static final String TAG = DisplayAsyncTask.class.getSimpleName();
    /**
     * Context受け取り用
     */
    @NonNull
    private final WeakReference<Context> weakReference;
    /**
     * コールバック設定用
     */
    @Nullable
    private DisplayCallback displayCallback;

    /**
     * コンストラクタ
     * {@inheritDoc}
     */
    public DisplayAsyncTask(@NonNull Context context) {
        super();
        weakReference = new WeakReference<>(context);
    }

    /**
     * DBの情報取得処理
     * <p>
     * {@inheritDoc}
     *
     * @return 保存成功：true　失敗：false
     */
    @NonNull
    @Override
    protected List<DivingLog> doInBackground(Integer... integers) {
        android.util.Log.d(TAG, "doInBackground");
        List<DivingLog> logList = new ArrayList<>();

        //　非同期での処理
        // DB作成
        OpenHelper openHelper = new OpenHelper(weakReference.get());

        // DBを開く
        SQLiteDatabase db = openHelper.getWritableDatabase();

        // 取得したいカラムを配列に格納
        String[] projection = {
                LogConstant.LOG_ID,
                LogConstant.DIVE_NUMBER,
                LogConstant.PLACE,
                LogConstant.POINT,
                LogConstant.DATE,
                LogConstant.TIME_DIVE,
                LogConstant.TIME_START,
                LogConstant.TIME_END,
                LogConstant.DEPTH_MAX,
                LogConstant.DEPTH_AVE,
                LogConstant.AIR_DIVE,
                LogConstant.AIR_START,
                LogConstant.AIR_END,
                LogConstant.WEATHER,
                LogConstant.TEMP,
                LogConstant.TEMP_WATER,
                LogConstant.VISIBILITY,
                LogConstant.MEMBER,
                LogConstant.MEMBER_NAVIGATE,
                LogConstant.MEMO,
                LogConstant.PICTURE
        };

        Cursor cursor = db.query(
                LogConstant.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        // 取得したデータをcursorリストに追加
        while (cursor.moveToNext()) {
            DivingLog log = getLog(cursor);
            logList.add(log);
        }
        cursor.close();

        // 失敗
        if (0 == logList.size()) {
            return null;
        }
        return logList;
    }

    /**
     * DBの情報が入ったcursorから情報を取り出し
     * 1つのDivingLogクラスのインスタンスを作成する
     *
     * @return DivingLog
     */
    @NonNull
    private DivingLog getLog(Cursor cursor) {
        DivingLog log = new DivingLog();
        // LogID
        int logId = cursor.getInt(
                cursor.getColumnIndexOrThrow(LogConstant.LOG_ID));
        log.setLogId(logId);
        // 本数
        int diveNumber = cursor.getInt(
                cursor.getColumnIndexOrThrow(LogConstant.DIVE_NUMBER));
        log.setDiveNumber(diveNumber);
        // 場所
        String place = cursor.getString(
                cursor.getColumnIndexOrThrow(LogConstant.PLACE));
        log.setPlace(place);
        // ポイント
        String point = cursor.getString(
                cursor.getColumnIndexOrThrow(LogConstant.POINT));
        log.setPoint(point);
        // 日付
        String date = cursor.getString(
                cursor.getColumnIndexOrThrow(LogConstant.DATE));
        log.setDate(date);
        // 潜水時間
        String time = cursor.getString(
                cursor.getColumnIndexOrThrow(LogConstant.TIME_DIVE));
        log.setTimeDive(time);
        // 潜水開始時間
        String timeStart = cursor.getString(
                cursor.getColumnIndexOrThrow(LogConstant.TIME_START));
        log.setTimeStart(timeStart);
        // 潜水終了時間
        String timeEnd = cursor.getString(
                cursor.getColumnIndexOrThrow(LogConstant.TIME_END));
        log.setTimeEnd(timeEnd);
        // 最大深度
        int depthMax = cursor.getInt(
                cursor.getColumnIndexOrThrow(LogConstant.DEPTH_MAX));
        log.setDepthMax(depthMax);
        // 平均深度
        int depthAve = cursor.getInt(
                cursor.getColumnIndexOrThrow(LogConstant.DEPTH_AVE));
        log.setDepthAve(depthAve);
        // 使用酸素
        int air = cursor.getInt(
                cursor.getColumnIndexOrThrow(LogConstant.AIR_DIVE));
        log.setAirDive(air);
        // 開始時の酸素量
        int airStart = cursor.getInt(
                cursor.getColumnIndexOrThrow(LogConstant.AIR_START));
        log.setAirStart(airStart);
        // 終了時の酸素量
        int airEnd = cursor.getInt(
                cursor.getColumnIndexOrThrow(LogConstant.AIR_END));
        log.setAirEnd(airEnd);
        // 天気
        String weather = cursor.getString(
                cursor.getColumnIndexOrThrow(LogConstant.WEATHER));
        log.setWeather(weather);
        // 気温
        double temp = cursor.getDouble(
                cursor.getColumnIndexOrThrow(LogConstant.TEMP));
        log.setTemp(temp);
        // 水温
        double waterTemp = cursor.getDouble(
                cursor.getColumnIndexOrThrow(LogConstant.TEMP_WATER));
        log.setTempWater(waterTemp);
        // 透明度
        int visibility = cursor.getInt(
                cursor.getColumnIndexOrThrow(LogConstant.VISIBILITY));
        log.setVisibility(visibility);
        // メンバー
        String member = cursor.getString(
                cursor.getColumnIndexOrThrow(LogConstant.MEMBER));
        log.setMember(member);
        // ナビ
        String navi = cursor.getString(
                cursor.getColumnIndexOrThrow(LogConstant.MEMBER_NAVIGATE));
        log.setMemberNavigate(navi);
        // メモ
        String memo = cursor.getString(
                cursor.getColumnIndexOrThrow(LogConstant.MEMO));
        log.setMemo(memo);
        // 写真
        String pictureUri = cursor.getString(
                cursor.getColumnIndexOrThrow(LogConstant.PICTURE));
        log.setPictureUri(pictureUri);

        return log;
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
    protected void onPostExecute(List<DivingLog> logList) {
        super.onPostExecute(logList);
        if (null != displayCallback) {
            if (null != logList) {
                displayCallback.onSuccess(logList);
            } else {
                displayCallback.onFailure();
            }
        }
    }

    /**
     * コールバック処理を設定
     *
     * @param displayCallback 　コールバックする内容
     */
    public void setOnCallBack(@Nullable DisplayCallback displayCallback) {
        this.displayCallback = displayCallback;
    }

    /**
     * コールバック用インターフェイス
     */
    public interface DisplayCallback {
        void onSuccess(List<DivingLog> logList);

        void onFailure();
    }
}