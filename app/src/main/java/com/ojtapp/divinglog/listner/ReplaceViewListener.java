package com.ojtapp.divinglog.listner;

import androidx.annotation.NonNull;

import com.ojtapp.divinglog.appif.DivingLog;

public interface ReplaceViewListener {
    /**
     * 編集画面に移行する
     *
     * @param divingLog 　更新対象のデータをもつDivingLogクラス
     */
    void replaceToEditFragment(@NonNull DivingLog divingLog);

    /**
     * 詳細画面に移行する
     *
     * @param divingLog 　詳細表示対象のデータをもつDivingLogクラス
     */
    void replaceToDetailFragment(@NonNull DivingLog divingLog);

//    void replaceToMainActivity(@NonNull DivingLog divingLog);


}
