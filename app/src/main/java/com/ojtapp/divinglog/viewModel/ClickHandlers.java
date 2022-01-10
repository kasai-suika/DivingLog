package com.ojtapp.divinglog.viewModel;

import android.view.View;

public interface ClickHandlers {

    /**
     * 「作成」ボタンクリック時
     *
     * @param view ボタン
     */
    void onMakeClick(View view);

    /**
     * 「削除」ボタンクリック時
     *
     * @param view 　ボタン
     */
    void onDeleteClick(View view);

    /**
     * 「編集」ボタンクリック時
     *
     * @param view 　ボタン
     */
    void onEditClick(View view);
}
