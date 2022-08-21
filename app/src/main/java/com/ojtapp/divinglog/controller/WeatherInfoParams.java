package com.ojtapp.divinglog.controller;

import androidx.annotation.NonNull;

public class WeatherInfoParams {
    public static class PlaceName extends WeatherInfoParams {
        public String place;

        public PlaceName(@NonNull String place) {
            this.place = place;
        }
    }

    public static class GeographicCoordinates extends WeatherInfoParams {
        public String latitude;
        public String longitude;

        public GeographicCoordinates(@NonNull String latitude, @NonNull String longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
}
