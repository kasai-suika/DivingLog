package com.ojtapp.divinglog.view.detail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.ojtapp.divinglog.R;
import com.ojtapp.divinglog.appif.DivingLog;
import com.ojtapp.divinglog.databinding.FragmentDetailLogBinding;
import com.ojtapp.divinglog.viewModel.MainViewModel;

public class LogDetailFragment extends Fragment {
    /**
     * クラス名
     */
    private static final String TAG = LogDetailFragment.class.getSimpleName();
    /**
     * DivingLogオブジェクト受け取り用キー
     */
    private static final String LOG_KEY = "DIVE_LOG";
    /**
     * 編集ボタン押下のコールバック
     */
    private OnDetailFragmentEditButtonListener callback;
    private DivingLog divingLog;

    /**
     * デフォルトコンストラクタ
     */
    public LogDetailFragment() {
    }

    /**
     * フラグメントのインスタンスを作成
     *
     * @return フラグメント
     */
    public static Fragment newInstance(@NonNull DivingLog divingLog) {
        android.util.Log.d(TAG, "newInstance()");

        LogDetailFragment fragment = new LogDetailFragment();

        Bundle args = new Bundle();
        args.putSerializable(LOG_KEY, divingLog);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStat) {
        Bundle args = getArguments();
        FragmentDetailLogBinding binding = null;
        if (null != args) {
            divingLog = (DivingLog) args.getSerializable(LOG_KEY);
            MainViewModel viewModel = new MainViewModel(requireContext(), divingLog);
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_log, container, false);
            binding.setMain(viewModel);
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceStat) {
        super.onViewCreated(view, savedInstanceStat);
        // 編集ボタン押下時の設定
        Button editButton = view.findViewById(R.id.detail_button_edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "編集ボタン押下");
                callback.onDetailFragmentEditButton(divingLog);
            }
        });
    }

    public void setOnDetailFragmentEditButtonListener(@Nullable OnDetailFragmentEditButtonListener callback) {
        this.callback = callback;
    }

    public interface OnDetailFragmentEditButtonListener {
        void onDetailFragmentEditButton(DivingLog divingLog);
    }
}
