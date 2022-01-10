package com.ojtapp.divinglog.view.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ojtapp.divinglog.R;
import com.ojtapp.divinglog.view.dialog.SortMenu;
import com.ojtapp.divinglog.appif.DivingLog;
import com.ojtapp.divinglog.controller.DisplayAsyncTask;
import com.ojtapp.divinglog.util.SharedPreferencesUtil;

import java.util.List;

/**
 * リスト表示フラグメント
 */
public class LogFragment extends Fragment {
    private static final String TAG = LogFragment.class.getSimpleName();
    private OnListItemListener callback;
    private ListView listView;

    public LogFragment() {
    }

    /**
     * フラグメントのインスタンスを作成
     * {@inheritDoc}
     */
    public static LogFragment newInstance() {
        android.util.Log.d(TAG, "newInstance()");

        LogFragment fragment = new LogFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Viewを作成
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStat) {
        return inflater.inflate(R.layout.fragment_log, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceStat) {
        super.onViewCreated(view, savedInstanceStat);
        // リスト表示のViewを結び付ける
        listView = view.findViewById(R.id.list_view_log);
        Log.d(TAG, "listView = " + listView);

        // リスト押下時
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick");
                DivingLog divingLog = (DivingLog) parent.getItemAtPosition(position);
                callback.onListItem(divingLog);
            }
        });
        // データを取得し、画面を更新処理
        refreshView();
    }

    public void refreshView() {
        Log.d(TAG, "refreshView()");
        DisplayAsyncTask displayAsyncTask = new DisplayAsyncTask(requireContext());

        // コールバック処理
        displayAsyncTask.setOnCallBack(new DisplayAsyncTask.DisplayCallback() {
            @Override
            public void onSuccess(List<DivingLog> logList) {
                Log.d(TAG, "データ取得に成功しました");
                int memorySortMode = SharedPreferencesUtil.getSortMode(SharedPreferencesUtil.KEY_SORT_MODE, MainActivity.sharedPreferences);
                SortMenu.sortDivingLog(logList, memorySortMode);

                // Adapterの設定
                LogAdapter logAdapter = new LogAdapter(requireContext(), R.layout.list_log_item, logList);
                listView.setAdapter(logAdapter);
            }

            @Override
            public void onFailure() {
                Log.e(TAG, "データ取得に失敗しました");
            }
        });

        // 非同期処理のメソッドに移動
        displayAsyncTask.execute(0);
    }

    public void setOnListItemListener(OnListItemListener callback) {
        this.callback = callback;
    }

    public interface OnListItemListener {
        void onListItem(DivingLog divingLog);
    }
}
