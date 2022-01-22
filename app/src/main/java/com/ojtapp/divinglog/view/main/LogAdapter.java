package com.ojtapp.divinglog.view.main;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ojtapp.divinglog.R;
import com.ojtapp.divinglog.appif.DivingLog;
import com.ojtapp.divinglog.listner.ReplaceViewListener;

import java.util.List;

public class LogAdapter extends ArrayAdapter<DivingLog> {
    private static final String TAG = LogAdapter.class.getSimpleName();
    private final int resource;
    private final LayoutInflater inflater;
    /**
     * 画面移行するリスナー
     */
    private ReplaceViewListener listener;

    /**
     * コンストラクタ
     */
    public LogAdapter(@NonNull Context context, int resource, List<DivingLog> items) {
        super(context, resource, items);
        this.resource = resource;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    @NonNull
    public View getView(final int position, View convertView, @NonNull final ViewGroup parent) {
        View view;
        // Viewの再利用
        if (convertView != null) {
            view = convertView;
        } else {
            view = inflater.inflate(resource, null);
        }

        // DivingLogを取得
        final DivingLog divingLog = getItem(position);
        if (divingLog != null) {
            // 本数
            TextView divingNumber = view.findViewById(R.id.list_diving_number);
            divingNumber.setText(String.valueOf(divingLog.getDivingNumber()));

            // 場所
            TextView place = view.findViewById(R.id.list_place);
            place.setText(divingLog.getPlace());

            // ポイント
            TextView point = view.findViewById(R.id.list_point);
            point.setText(divingLog.getPoint());
        } else {
            Log.e(TAG, "divingLog = null");
        }

        // 編集ボタン押下
        ImageButton editButton = view.findViewById(R.id.list_button_edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((null != listener) && (null != divingLog)) {
                    listener.replaceToEditFragment(divingLog);
                }
            }
        });
        return view;
    }

    public void setReplaceViewListener(@Nullable ReplaceViewListener listener) {
        this.listener = listener;
    }
}
