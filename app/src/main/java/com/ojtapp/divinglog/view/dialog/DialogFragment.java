package com.ojtapp.divinglog.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;


public class DialogFragment extends androidx.fragment.app.DialogFragment {
    /**
     * クラス名
     */
    private static final String TAG = DialogFragment.class.getSimpleName();
    /**
     * ダイアログのタイトルキー
     */
    private static final String KEY_TITLE = "KEY_TITLE";
    /**
     * ダイアログのメッセージキー
     */
    private static final String KEY_MESSAGE = "KEY_MESSAGE";
    /**
     * ポジティブボタンの名称
     */
    private static final String BUTTON_POSITIVE = "はい";
    /**
     * ネガティブボタンの名称
     */
    private static final String BUTTON_NEGATIVE = "いいえ";
    /**
     * 「はい」押下時のコールバック
     */
    public OnClickButtonListener onClickButtonListener;

    /**
     * デフォルトコンストラクタ
     */
    public DialogFragment() {
    }

    /**
     * フラグメントのインスタンスを作成
     *
     * @param title   ダイアログのタイトル
     * @param message 　ダイアログのメッセージ
     * @return フラグメント
     */
    public static DialogFragment newInstance(@NonNull String title, @NonNull String message) {
        Log.d(TAG, "newInstance()");

        DialogFragment fragment = new DialogFragment();

        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        args.putString(KEY_MESSAGE, message);
        fragment.setArguments(args);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getContext();
        assert context != null;

        Bundle args = getArguments();
        if (null == args) {
            Log.e(TAG, "args = null");
        }

        final String title = args.getString(KEY_TITLE);
        final String message = args.getString(KEY_MESSAGE);

        return new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(BUTTON_POSITIVE, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onClickButtonListener.onClickPositiveButton();
                    }
                })
                .setNegativeButton(BUTTON_NEGATIVE, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onClickButtonListener.onClickNegativeButton();
                    }
                })
                .show();
    }


    /**
     * コールバック処理を設定
     *
     * @param onClickButtonListener 　コールバックする内容
     */
    public void setOnClickButtonListener(@Nullable OnClickButtonListener onClickButtonListener) {
        this.onClickButtonListener = onClickButtonListener;
    }

    /**
     * コールバック用インターフェイス
     */
    public interface OnClickButtonListener {
        void onClickPositiveButton();

        void onClickNegativeButton();
    }
}