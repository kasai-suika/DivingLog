package com.ojtapp.divinglog.BindingAdapter;

import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

import androidx.annotation.RestrictTo;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.InverseBindingMethod;
import androidx.databinding.InverseBindingMethods;
import androidx.databinding.adapters.ListenerUtil;
import androidx.databinding.library.baseAdapters.R;

/**
 * @hide
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
@InverseBindingMethods({
        @InverseBindingMethod(type = DatePicker.class, attribute = "android:year"),
        @InverseBindingMethod(type = DatePicker.class, attribute = "android:month"),
        @InverseBindingMethod(type = DatePicker.class, attribute = "android:day", method = "getDayOfMonth"),
})
public class DatePickerBindingAdapter {
    @BindingAdapter(value = {"android:year", "android:month", "android:day",
            "android:onDateChanged", "android:yearAttrChanged",
            "android:monthAttrChanged", "android:dayAttrChanged"}, requireAll = false)
    public static void setListeners(DatePicker view, int year, int month, int day,
                                    final OnDateChangedListener listener, final InverseBindingListener yearChanged,
                                    final InverseBindingListener monthChanged, final InverseBindingListener dayChanged) {
        if (year == 0) {
            year = view.getYear();
        }
        if (day == 0) {
            day = view.getDayOfMonth();
        }
        if (yearChanged == null && monthChanged == null && dayChanged == null) {
            view.init(year, month, day, listener);
        } else {
            DateChangedListener oldListener = ListenerUtil.getListener(view, R.id.onDateChanged);
            if (oldListener == null) {
                oldListener = new DateChangedListener();
                ListenerUtil.trackListener(view, oldListener, R.id.onDateChanged);
            }
            oldListener.setListeners(listener, yearChanged, monthChanged, dayChanged);
            view.init(year, month, day, oldListener);
        }
    }

    private static class DateChangedListener implements OnDateChangedListener {
        OnDateChangedListener mListener;
        InverseBindingListener mYearChanged;
        InverseBindingListener mMonthChanged;
        InverseBindingListener mDayChanged;

        public void setListeners(OnDateChangedListener listener, InverseBindingListener yearChanged,
                                 InverseBindingListener monthChanged, InverseBindingListener dayChanged) {
            mListener = listener;
            mYearChanged = yearChanged;
            mMonthChanged = monthChanged;
            mDayChanged = dayChanged;
        }

        @Override
        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            if (mListener != null) {
                mListener.onDateChanged(view, year, monthOfYear, dayOfMonth);
            }
            if (mYearChanged != null) {
                mYearChanged.onChange();
            }
            if (mMonthChanged != null) {
                mMonthChanged.onChange();
            }
            if (mDayChanged != null) {
                mDayChanged.onChange();
            }
        }
    }
}
