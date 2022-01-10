package com.ojtapp.divinglog.view.dialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.ojtapp.divinglog.R;

public class ProgressDialogFragment extends DialogFragment {
    private static final String DIALOG_TITLE = "DIALOG_TITLE";

    /**
     * デフォルトコンストラクタ
     */
    public ProgressDialogFragment() {
    }

    /**
     * フラグメントのインスタンスを作成
     *
     * @return フラグメント
     */
    public static ProgressDialogFragment newInstance(String dialogTitle) {
        ProgressDialogFragment progressDialogFragment = new ProgressDialogFragment();
        Bundle args = new Bundle();
        args.putString(DIALOG_TITLE, dialogTitle);
        progressDialogFragment.setArguments(args);

        return progressDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        String dialogTitle = "処理実行中";
        if (null != args) {
            dialogTitle = args.getString(DIALOG_TITLE);
        }

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_progress);
        dialog.setCancelable(false);
        dialog.setTitle(dialogTitle);
        return dialog;
    }
}
