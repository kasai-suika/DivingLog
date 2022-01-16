package com.ojtapp.divinglog.controller;

import android.location.Location;
import android.location.LocationListener;

import androidx.annotation.NonNull;

public class GPSLocationListener implements LocationListener {

    @Override
    public void onLocationChanged(@NonNull Location location) {
        // 緯度を取得
        Double a = location.getLatitude();
        // 経度を取得
        Double b = location.getLongitude();
    }
}
