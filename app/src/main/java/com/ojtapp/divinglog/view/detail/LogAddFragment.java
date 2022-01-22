package com.ojtapp.divinglog.view.detail;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ojtapp.divinglog.R;
import com.ojtapp.divinglog.databinding.FragmentAddLogBinding;
import com.ojtapp.divinglog.view.main.MainActivity;
import com.ojtapp.divinglog.viewModel.MainViewModel;

public class LogAddFragment extends Fragment {
    /**
     * クラスの名前
     */
    private static final String TAG = LogAddFragment.class.getSimpleName();
    /**
     * ViewModel
     */
    private MainViewModel viewModel;

    /**
     * デフォルトコンストラクタ
     */
    public LogAddFragment() {
    }

    /**
     * フラグメントのインスタンスを作成
     *
     * @return フラグメント
     */
    public static Fragment newInstance() {
        android.util.Log.d(TAG, "newInstance()");
        return new LogAddFragment();
    }

    /**
     * 作成画面のViewを作成
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStat) {
        android.util.Log.d(TAG, "onCreateView");
        viewModel = new MainViewModel(requireContext(), null);
        FragmentAddLogBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_log, container, false);
        binding.setLifecycleOwner(this);
        binding.setMain(viewModel);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceStat) {
        super.onViewCreated(view, savedInstanceStat);
        android.util.Log.d(TAG, "onViewCreated");

        // 写真追加ボタン押下
        FloatingActionButton selectPictureButton = view.findViewById(R.id.button_add_picture);
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
        Log.d("AddFrag", "in onActivityResult");
        if ((requestCode == MainActivity.REQUEST_CODE_OPEN_DOCUMENT) && (resultCode == Activity.RESULT_OK)) {
            if (null != resultData) {
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