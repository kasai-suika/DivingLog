package com.ojtapp.divinglog.view.detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ojtapp.divinglog.R;
import com.ojtapp.divinglog.appif.DivingLog;
import com.ojtapp.divinglog.databinding.FragmentEditLogBinding;
import com.ojtapp.divinglog.view.main.MainActivity;
import com.ojtapp.divinglog.viewModel.MainViewModel;

public class LogEditFragment extends Fragment {
    /**
     * クラス名
     */
    private static final String TAG = LogEditFragment.class.getSimpleName();
    /**
     * DivingLogオブジェクト受け取り用キー
     */
    private static final String LOG_KEY = "DIVE_LOG";
    /**
     * ViewModel
     */
    private MainViewModel viewModel;

    /**
     * デフォルトコンストラクタ
     */
    public LogEditFragment() {
    }

    /**
     * フラグメントのインスタンスを作成
     *
     * @return フラグメント
     */
    public static Fragment newInstance(@NonNull DivingLog divingLog) {
        android.util.Log.d(TAG, "newInstance()");

        LogEditFragment fragment = new LogEditFragment();

        Bundle args = new Bundle();
        args.putSerializable(LOG_KEY, divingLog);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStat) {
        Bundle args = getArguments();
        FragmentEditLogBinding binding = null;
        if (null != args) {
            DivingLog divingLog = (DivingLog) args.getSerializable(LOG_KEY);
            viewModel = new MainViewModel(requireContext(), divingLog);
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_log, container, false);
            binding.setMain(viewModel);
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceStat) {
        super.onViewCreated(view, savedInstanceStat);

        FloatingActionButton selectPictureButton = view.findViewById(R.id.button_add_picture);
        // 写真追加ボタン押下
        selectPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                startActivityForResult(intent, MainActivity.REQUEST_CODE_OPEN_DOCUMENT);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if ((requestCode == MainActivity.REQUEST_CODE_OPEN_DOCUMENT) && (resultCode == Activity.RESULT_OK)) {
            Context context = getContext();
            if ((resultData != null) && (context != null)) {
                Uri uri = resultData.getData();
                viewModel.uri.setValue(uri);

                // URIの権限を保持する
                final int takeFlags = resultData.getFlags()
                        & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                        | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                requireContext().getContentResolver().takePersistableUriPermission(uri, takeFlags);
            }
        }
    }
}
