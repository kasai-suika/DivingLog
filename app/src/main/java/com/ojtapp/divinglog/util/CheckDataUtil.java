package com.ojtapp.divinglog.util;

import com.ojtapp.divinglog.appif.DivingLog;
import com.ojtapp.divinglog.view.dialog.SortMenu;

public class CheckDataUtil {
    public static boolean checkDivingLog(DivingLog divingLog) {
        // 場所
        if (divingLog.getPlace() != null) {
            // 絵文字の入力禁止（入力された1文字が2バイト以上の物について、絵文字かどうか判断をする。）
            int codePoint = Character.codePointAt(divingLog.getPlace(), 0);

            if ((codePoint >= 0x1F300 && codePoint <= 0x1F5FF) ||
                    (codePoint >= 0x1F900 && codePoint <= 0x1F9FF) ||
                    (codePoint >= 0x1F600 && codePoint <= 0x1F64F) ||
                    (codePoint >= 0x1F680 && codePoint <= 0x1F6FF) ||
                    (codePoint >= 0x2600 && codePoint <= 0x26FF) ||
                    (codePoint >= 0x2700 && codePoint <= 0x27BF)) {
                return false;
            }
        }

        // 本数
        if ((divingLog.getDivingNumber() == ConversionUtil.NO_DATA) ||
                (divingLog.getDivingNumber() <= 0) ||
                (divingLog.getDivingNumber() >= 200)) {
            return false;
        }
        return true;
    }

    public static boolean checkSortModeValue(int sortModeValue){
        if ((SortMenu.INDEX_SORTMODE_MAX_VALUE < sortModeValue) ||
                (SortMenu.INDEX_SORTMODE_MIN_VALUE > sortModeValue)) {
            return false;
        }
        return true;
    }
}
