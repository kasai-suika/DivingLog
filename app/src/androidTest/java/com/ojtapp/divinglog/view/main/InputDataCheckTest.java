package com.ojtapp.divinglog.view.main;

import com.ojtapp.divinglog.appif.DivingLog;
import com.ojtapp.divinglog.util.CheckDataUtil;

import org.junit.Assert;
import org.junit.Test;

public class InputDataCheckTest {
    private static final Object EMOJI = "\uD83D\uDE42";    // 🙂
    private static final Object[][] INPUT_STR = {
            {null, true},
            {"abc", true},
            {"あいう", true},
            {"123", true},
            {"カタカナ", true},
            {EMOJI, false}};

    private static final Object[][] INPUT_INT = {
            {-1, false},
            {0, false},
            {199, true},
            {200, false}};

    /**
     * 文字列入力テスト
     */
    @Test
    public void inputStringData() {
        DivingLog divingLog = new DivingLog();
        divingLog.setDiveNumber(1);

        // 「場所」の入力でテスト
        for (Object[] test : INPUT_STR) {
            divingLog.setPlace((String) test[0]);
            Assert.assertEquals(test[1], CheckDataUtil.checkDivingLog(divingLog));
        }
    }

    /**
     * 数値入力テスト
     */
    @Test
    public void inputIntData() {
        DivingLog divingLog = new DivingLog();

        // 「本数」の入力でテスト
        for (Object[] test : INPUT_INT) {
            divingLog.setDiveNumber((int) test[0]);
            Assert.assertEquals(test[1], CheckDataUtil.checkDivingLog(divingLog));
        }
    }
}
