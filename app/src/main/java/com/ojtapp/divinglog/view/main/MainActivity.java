package com.ojtapp.divinglog.view.main;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
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
    private FusedLocationProviderClient fusedLocationClient;
    private Location location = null;
    public static SharedPreferences sharedPreferences;
    public static final int RESULT_PICK_IMAGEFILE = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getApplicationContext().getSharedPreferences(SharedPreferencesUtil.FILE_NAME_SORT, Context.MODE_PRIVATE);

        // LocationClientクラスのインスタンスを生成
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // 位置情報取得開始
        startUpdateLocation(getApplicationContext());

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
        if (requestCode == 2000 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
                MainActivity.this.location = locationResult.getLastLocation();
            }

            ;
        }, null);
    }

    public Location getLocation() {
        return this.location;
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
     *
     * @param divingLog 　押下されたリストアイテムが保持するデータをもつDivingLogクラス
     */
    @Override
    public void onListItem(@NonNull DivingLog divingLog) {
        LogDetailFragment fragment = (LogDetailFragment) LogDetailFragment.newInstance(divingLog);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    /**
     * 詳細画面で「更新ボタン」が押下された場合、
     * 編集画面に移行する
     *
     * @param divingLog 　更新対象のデータをもつDivingLogクラス
     */
    @Override
    public void onDetailFragmentEditButton(@NonNull DivingLog divingLog) {
        LogEditFragment fragment = (LogEditFragment) LogEditFragment.newInstance(divingLog);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    /**
     * リスト内の「更新ボタン」が押下された場合、
     * 編集画面に移行する
     *
     * @param view 　押下されたリスト
     */
    public void onListEditButton(@NonNull View view) {
        DivingLog divingLog = (DivingLog) view.getTag();
        LogEditFragment fragment = (LogEditFragment) LogEditFragment.newInstance(divingLog);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}