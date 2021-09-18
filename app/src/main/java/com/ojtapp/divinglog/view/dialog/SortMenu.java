package com.ojtapp.divinglog.view.dialog;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ojtapp.divinglog.appif.DivingLog;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortMenu {
    private static final String TAG = SortMenu.class.getSimpleName();
    private static final int INDEX_SORTMODE_MAX_VALUE = 1;
    private static final int INDEX_SORTMODE_MIN_VALUE = 0;

    public static void sortDivingLog(@NonNull List<DivingLog> divingLogList, int sortModeValue) {
        Log.d(TAG, "sortDivingLog");
        if ((INDEX_SORTMODE_MAX_VALUE < sortModeValue) || (INDEX_SORTMODE_MIN_VALUE > sortModeValue)) {
            throw new IllegalArgumentException("引数'sortModeValue'の値が不適切です。　sortModeValue =" + sortModeValue);
        }

        SortModes sortMode = SortModes.values()[sortModeValue];

        if (SortModes.UP_MODE == sortMode) {
            divingLogList.sort(new Comparator<DivingLog>() {
                @Override
                public int compare(DivingLog o1, DivingLog o2) {
                    Log.d(TAG, SortModes.UP_MODE.str);
                    return o1.getDivingNumber() - o2.getDivingNumber();
                }
            });
        } else if (SortModes.DOWN_MODE == sortMode) {
            divingLogList.sort(new Comparator<DivingLog>() {
                @Override
                public int compare(DivingLog o1, DivingLog o2) {
                    Log.d(TAG, SortModes.DOWN_MODE.str);
                    return o2.getDivingNumber() - o1.getDivingNumber();
                }
            });
        } else {
            throw new IndexOutOfBoundsException("対象のソートモードがありません");
        }
    }

    public enum SortModes {
        UP_MODE("No（昇順）"),
        DOWN_MODE("No（降順）");

        public final String str;

        SortModes(@NonNull String value) {
            this.str = value;
        }

        public static String[] getSortModesStrArray() {
            List<String> list = new ArrayList<>();
            for (SortModes mode : values()) {
                list.add(mode.str);
            }
            String[] strArray = new String[list.size()];
            return list.toArray(strArray);
        }
    }
}
