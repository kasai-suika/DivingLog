package com.ojtapp.divinglog.listner;

import androidx.annotation.NonNull;

import com.ojtapp.divinglog.appif.DivingLog;

public interface OnReplaceViewButtonClickListener {
    /**
     * 編集画面に移行する
     *
     * @param divingLog 　更新対象のデータをもつDivingLogクラス
     */
    void onReplaceToEditFragmentButtonClick(@NonNull DivingLog divingLog);

    /**
     * 詳細画面に移行する
     *
     * @param divingLog 　詳細表示対象のデータをもつDivingLogクラス
     */
    void onReplaceToDetailFragmentButtonClick(@NonNull DivingLog divingLog);

//    void replaceToMainActivity(@NonNull DivingLog divingLog);


}
