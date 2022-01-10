package com.ojtapp.divinglog.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class SortDialogFragment extends DialogFragment {
    /**
     * クラス名
     */
    private final static String TAG = SortDialogFragment.class.getSimpleName();
    /**
     * 並び替えモードのキー
     */
    private static final String KEY_SORT_MODE_POSITION = "KEY_SORT_MODE_POSITION";
    /**
     * ポジティブボタンの名称
     */
    private static final String BUTTON_POSITIVE = "OK";
    /**
     * ネガティブボタンの名称
     */
    private static final String BUTTON_NEGATIVE = "NG";
    /**
     * コールバック
     */
    private SortDialogCallback sortDialogCallback;

    /**
     * デフォルトコンストラクタ
     */
    public SortDialogFragment() {
    }

    /**
     * フラグメントのインスタンスを作成
     *
     * @return フラグメント
     */
    public static SortDialogFragment newInstance(int sortModePosition) {
        Log.d(TAG, "newInstance");
        SortDialogFragment fragment = new SortDialogFragment();

        Bundle args = new Bundle();
        args.putInt(KEY_SORT_MODE_POSITION, sortModePosition);
        fragment.setArguments(args);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        Log.d(TAG, "onCreateDialog");
        Bundle args = getArguments();
        if (null == args) {
            throw new IllegalStateException("予期せぬエラー");
        }

        final int[] sortModePosition = {args.getInt(KEY_SORT_MODE_POSITION)};

        return new AlertDialog.Builder(getContext())
                .setSingleChoiceItems(SortMenu.SortModes.getSortModesStrArray(), sortModePosition[0], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sortModePosition[0] = which;
                    }
                })
                .setPositiveButton(BUTTON_POSITIVE, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sortDialogCallback.onSortDialog(sortModePosition[0]);
                    }
                })
                .setNegativeButton(BUTTON_NEGATIVE, null)
                .show();
    }

    /**
     * コールバックを設定する
     *
     * @param sortDialogCallback 　コールバックでの動作
     */
    public void setOnCallback(@Nullable SortDialogCallback sortDialogCallback) {
        if (null != sortDialogCallback) {
            this.sortDialogCallback = sortDialogCallback;
        }
    }

    /**
     * コールバックのインターフェース
     */
    public interface SortDialogCallback {
        void onSortDialog(int position);
    }
}
