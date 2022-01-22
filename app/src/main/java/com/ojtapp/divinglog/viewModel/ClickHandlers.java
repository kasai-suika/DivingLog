package com.ojtapp.divinglog.viewModel;

import android.view.View;

public interface ClickHandlers {

    /**
     * 「作成」ボタンクリック時
     */
    void onMakeClick(View view);

    /**
     * 「削除」ボタンクリック時
     */
    void onDeleteClick(View view);

    /**
     * 「編集」ボタンクリック時
     */
    void onEditClick(View view);
}
