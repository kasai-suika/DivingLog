package com.ojtapp.divinglog.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.ojtapp.divinglog.constant.LogConstant;
import com.ojtapp.divinglog.appif.DivingLog;
import com.ojtapp.divinglog.model.OpenHelper;
import com.ojtapp.divinglog.view.dialog.ProgressDialogFragment;

import java.lang.ref.WeakReference;

/**
 * Log情報をDBから削除するクラス
 */
public class DeleteAsyncTask extends AsyncTask<DivingLog, Integer, Boolean> {
    /**
     * クラス名
     */
    private static final String TAG = DeleteAsyncTask.class.getSimpleName();
    /**
     * Context受け取り用
     */
    @NonNull
    private final WeakReference<Context> contextWeakReference;
    /**
     * Activity受け取り用
     */
    @NonNull
    private final WeakReference<FragmentActivity> activityWeakReference;

    private ProgressDialogFragment progressDialogFragment = null;
    /**
     * コールバック設定用
     */
    @Nullable
    private DeleteCallback deleteCallback;

    public DeleteAsyncTask(@NonNull Context context, FragmentActivity activity) {
        super();
        this.contextWeakReference = new WeakReference<>(context);
        this.activityWeakReference = new WeakReference<>(activity);
    }

    /**
     * 実行前の事前処理
     */
    @Override
    protected void onPreExecute() {
        FragmentActivity fragmentActivity = activityWeakReference.get();
        assert fragmentActivity != null;
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();

        // プログレスダイアログの生成
        this.progressDialogFragment = ProgressDialogFragment.newInstance("削除処理中");
        this.progressDialogFragment.show(fragmentManager, null);
    }

    @Override
    protected Boolean doInBackground(DivingLog... divingLogs) {
        Log.d(TAG, "doInBackground()");

        // 失敗
        if (null == divingLogs[0]) {
            return false;
        }

        OpenHelper openHelper = new OpenHelper(contextWeakReference.get());
        SQLiteDatabase db = openHelper.getWritableDatabase();

        String selection = LogConstant.LOG_ID + " = ?";
        String[] selectionArgs = {String.valueOf(divingLogs[0].getLogId())};
        db.delete(LogConstant.TABLE_NAME, selection, selectionArgs);
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... integers) {
    }

    /**
     * 非同期処理終了時にコールバックを呼び出す
     * {@inheritDoc}
     */
    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (progressDialogFragment != null && progressDialogFragment.getShowsDialog()) {
            progressDialogFragment.dismiss(); //ダイアログを閉じる
        }
        if (null != deleteCallback) {
            if (result) {
                deleteCallback.onSuccess();
            } else {
                deleteCallback.onFailure();
            }
        }
    }

    /**
     * コールバック処理を設定
     *
     * @param deleteCallback 　コールバックする内容
     */
    public void setDeleteCallback(@Nullable DeleteCallback deleteCallback) {
        this.deleteCallback = deleteCallback;
    }

    /**
     * コールバック用インターフェイス
     */
    public interface DeleteCallback {
        void onSuccess();

        void onFailure();
    }
}
