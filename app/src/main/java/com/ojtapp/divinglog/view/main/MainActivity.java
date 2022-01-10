package com.ojtapp.divinglog.view.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ojtapp.divinglog.R;
import com.ojtapp.divinglog.appif.DivingLog;
import com.ojtapp.divinglog.util.SharedPreferencesUtil;
import com.ojtapp.divinglog.view.detail.LogAddFragment;
import com.ojtapp.divinglog.view.detail.LogDetailFragment;
import com.ojtapp.divinglog.view.detail.LogEditFragment;
import com.ojtapp.divinglog.view.dialog.SortDialogFragment;

public class MainActivity extends AppCompatActivity implements LogFragment.OnListItemListener, LogDetailFragment.OnDetailFragmentEditButtonListener {
    /**
     * クラス名
     */
    private static final String TAG = MainActivity.class.getSimpleName();
    private LogFragment targetFragment;
    public static SharedPreferences sharedPreferences;
    public static final int RESULT_PICK_IMAGEFILE = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getApplicationContext().getSharedPreferences(SharedPreferencesUtil.FILE_NAME_SORT, Context.MODE_PRIVATE);

        // アクションボタンとViewの紐づけ
        FloatingActionButton addButton = findViewById(R.id.button_add);
        // 追加ボタンがクリックされた時の動作
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "追加ボタン押下");
                LogAddFragment fragment = (LogAddFragment) LogAddFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }
        });

        targetFragment = LogFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, targetFragment).commit();
    }

    /**
     * メニューの作成
     * {@inheritDoc}
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * 「並び替え」をクリックされた時の動作
     * {@inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        int memorySortMode;
        memorySortMode = SharedPreferencesUtil.getSortMode(SharedPreferencesUtil.KEY_SORT_MODE, sharedPreferences);

        SortDialogFragment sortDialogFragment = SortDialogFragment.newInstance(memorySortMode);

        sortDialogFragment.setOnCallback(new SortDialogFragment.SortDialogCallback() {
            @Override
            public void onSortDialog(int position) {
                Log.d(TAG, "SortDialogCallback");
                // 選択されたソートモードを記憶
                SharedPreferencesUtil.setInt(SharedPreferencesUtil.KEY_SORT_MODE, position, sharedPreferences);
                targetFragment.refreshView();
            }
        });
        sortDialogFragment.show(getSupportFragmentManager(), null);
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        if (fragment instanceof LogFragment) {
            LogFragment logFragment = (LogFragment) fragment;
            logFragment.setOnListItemListener(this);
        } else if (fragment instanceof LogDetailFragment) {
            LogDetailFragment logDetailFragment = (LogDetailFragment) fragment;
            logDetailFragment.setOnDetailFragmentEditButtonListener(this);
        }
    }

    /**
     * リストアイテムが押下された場合、
     * 詳細画面に移行する
     * @param divingLog　押下されたリストアイテムが保持するデータをもつDivingLogクラス
     */
    @Override
    public void onListItem(@NonNull DivingLog divingLog) {
        LogDetailFragment fragment = (LogDetailFragment) LogDetailFragment.newInstance(divingLog);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    /**
     * 詳細画面で「更新ボタン」が押下された場合、
     * 編集画面に移行する
     * @param divingLog　更新対象のデータをもつDivingLogクラス
     */
    @Override
    public void onDetailFragmentEditButton(@NonNull DivingLog divingLog) {
        LogEditFragment fragment = (LogEditFragment) LogEditFragment.newInstance(divingLog);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    /**
     * リスト内の「更新ボタン」が押下された場合、
     * 編集画面に移行する
     * @param view　押下されたリスト
     */
    public void onListEditButton(@NonNull View view) {
        DivingLog divingLog = (DivingLog) view.getTag();
        LogEditFragment fragment = (LogEditFragment) LogEditFragment.newInstance(divingLog);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}