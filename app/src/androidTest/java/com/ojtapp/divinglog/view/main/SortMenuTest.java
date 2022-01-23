package com.ojtapp.divinglog.view.main;

import com.ojtapp.divinglog.util.CheckDataUtil;
import com.ojtapp.divinglog.view.dialog.SortMenu;

import org.junit.Assert;
import org.junit.Test;

public class SortMenuTest {
    private static final Object[][] SORT_MODE_VALUE = {
            {SortMenu.INDEX_SORTMODE_MAX_VALUE, true},
            {SortMenu.INDEX_SORTMODE_MIN_VALUE, true},
            {SortMenu.INDEX_SORTMODE_MAX_VALUE + 1, false},
            {SortMenu.INDEX_SORTMODE_MIN_VALUE - 1, false}};

    @Test
    public void setSortModeValue() {
        for (Object[] test : SORT_MODE_VALUE) {
            Assert.assertEquals(test[1], CheckDataUtil.checkSortModeValue((int) test[0]));
        }
    }
}
