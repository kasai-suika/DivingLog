package com.ojtapp.divinglog.view.main;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ojtapp.divinglog.R;
import com.ojtapp.divinglog.appif.DivingLog;
import com.ojtapp.divinglog.listner.OnReplaceViewButtonClickListener;
import com.ojtapp.divinglog.util.SharedPreferencesUtil;
import com.ojtapp.divinglog.view.detail.LogAddFragment;
import com.ojtapp.divinglog.view.detail.LogDetailFragment;
import com.ojtapp.divinglog.view.detail.LogEditFragment;
import com.ojtapp.divinglog.view.dialog.SortDialogFragment;
import com.ojtapp.divinglog.viewModel.MainViewModel;

public class MainActivity extends AppCompatActivity implements OnReplaceViewButtonClickListener {
    /**
     * クラス名
     */
    private static final String TAG = MainActivity.class.getSimpleName();
    private LogFragment targetFragment;
    private FusedLocationProviderClient fusedLocationClient;
    public static SharedPreferences sharedPreferences;
    public static final int REQUEST_CODE_OPEN_DOCUMENT = 1000;
    private static final int REQUEST_CODE_FINE_LOCATION = 2000;
    private static final String[] PERMISSIONS_FINE_LOCATION = {Manifest.permission.ACCESS_FINE_LOCATION};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getApplicationContext().getSharedPreferences(SharedPreferencesUtil.FILE_NAME_SORT, Context.MODE_PRIVATE);

        // LocationClientクラスのインスタンスを生成
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // 位置情報取得開始
        startUpdateLocation(this);

        // 追加ボタンがクリックされた時の動作
        FloatingActionButton addButton = findViewById(R.id.button_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "追加ボタン押下");
                LogAddFragment fragment = (LogAddFragment) LogAddFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }
        });

        MainViewModel viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
        viewModel.checkLocationPermission().observe(this, observe -> {
            // 権限がない場合、許可ダイアログ表示
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_FINE_LOCATION, REQUEST_CODE_FINE_LOCATION);
            }
        });

        // 画面遷移
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
     * 許可ダイアログの結果受取
     * {@inheritDoc}
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ((requestCode == REQUEST_CODE_FINE_LOCATION)
                && (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                && (permissions == PERMISSIONS_FINE_LOCATION)) {
            // 位置情報取得開始
            startUpdateLocation(this);
        }
    }

    /**
     * 位置情報取得開始メソッド
     */
    private void startUpdateLocation(Context context) {
        // 位置情報取得権限の確認
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 権限がない場合、許可ダイアログ表示
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, permissions, 2000);
            return;
        }

        // 位置情報の取得方法を設定
        LocationRequest locationRequest = LocationRequest.create();
        fusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                // 現在値を取得
                MainViewModel.setLocation(locationResult.getLastLocation());
            }
        }, null);
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
            logFragment.setOnReplaceViewButtonClickListener(this);
        } else if (fragment instanceof LogDetailFragment) {
            LogDetailFragment logDetailFragment = (LogDetailFragment) fragment;
            logDetailFragment.setOnReplaceViewButtonClickListener(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onReplaceToEditFragmentButtonClick(@NonNull DivingLog divingLog) {
        LogEditFragment fragment = (LogEditFragment) LogEditFragment.newInstance(divingLog);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onReplaceToDetailFragmentButtonClick(@NonNull DivingLog divingLog) {
        LogDetailFragment fragment = (LogDetailFragment) LogDetailFragment.newInstance(divingLog);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
//
//    @Override
//    public void replaceToActivity() {
//        // 情報をintentに詰める
//        Intent intent = new Intent(this, MainActivity.class);
//        // 指定したアクティビティより上のViewを削除
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        this.startActivity(intent);
//    }
}