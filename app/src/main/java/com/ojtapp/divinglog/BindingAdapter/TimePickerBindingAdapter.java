package com.ojtapp.divinglog.BindingAdapter;

import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import androidx.annotation.RestrictTo;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

/**
 * @hide
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public class TimePickerBindingAdapter {
    @BindingAdapter("android:hour")
    public static void setHour(TimePicker view, int hour) {
        if (view.getHour() != hour) {
            view.setHour(hour);
        }
    }

    @BindingAdapter("android:minute")
    public static void setMinute(TimePicker view, int minute) {
        if (view.getMinute() != minute) {
            view.setMinute(minute);
        }
    }

    @InverseBindingAdapter(attribute = "android:hour")
    public static int getHour(TimePicker view) {
        return view.getHour();
    }

    @InverseBindingAdapter(attribute = "android:minute")
    public static int getMinute(TimePicker view) {
        return view.getMinute();
    }

    @BindingAdapter(value = {"android:onTimeChanged", "android:hourAttrChanged",
            "android:minuteAttrChanged"}, requireAll = false)
    public static void setListeners(TimePicker view, final OnTimeChangedListener listener,
                                    final InverseBindingListener hourChange, final InverseBindingListener minuteChange) {
        if (hourChange == null && minuteChange == null) {
            view.setOnTimeChangedListener(listener);
        } else {
            view.setOnTimeChangedListener(new OnTimeChangedListener() {
                @Override
                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                    if (listener != null) {
                        listener.onTimeChanged(view, hourOfDay, minute);
                    }
                    if (hourChange != null) {
                        hourChange.onChange();
                    }
                    if (minuteChange != null) {
                        minuteChange.onChange();
                    }
                }
            });
        }
    }
}
