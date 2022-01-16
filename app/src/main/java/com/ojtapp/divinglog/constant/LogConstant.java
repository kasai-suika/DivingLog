package com.ojtapp.divinglog.constant;

public class LogConstant {
    public static final String TABLE_NAME = "divingLog";
    public static final String LOG_ID = "logId";
    public static final String DIVE_NUMBER = "diveNumber";
    public static final String PLACE = "place";
    public static final String POINT = "point";
    public static final String DATE = "date";
    public static final String TIME_START = "timeStart";
    public static final String TIME_END = "timeEnd";
    public static final String TIME_DIVE = "timeDive"; //DBになくていい
    public static final String DEPTH_MAX = "depthMax";
    public static final String DEPTH_AVE = "depthAve";
    public static final String AIR_START = "airStart";
    public static final String AIR_END = "airEnd";
    public static final String AIR_DIVE = "airDive";//DBになくていい
    public static final String WEATHER = "weather";
    public static final String TEMP = "tempAir";
    public static final String TEMP_WATER = "tempWater";
    public static final String VISIBILITY = "visibility";
    public static final String MEMBER_NAVIGATE = "member_navigate";
    public static final String MEMBER = "member";
    public static final String MEMO = "memo";
    public static final String PICTURE = "picture";
    /**
     * 日付フォーマット
     */
    public static final String FORMAT_DATE = "yyyy/MM/dd";
    /**
     * 時間フォーマット
     */
    public static final String FORMAT_TIME = "HH:mm";
    /**
     * 削除確認ダイアログのタイトル
     */
    public static final String TITLE_DELETE_DIALOG = "削除確認";
    /**
     * 削除確認ダイアログのメッセージ
     */
    public static final String MESSAGE_DELETE_DIALOG = "削除してもよろしいですか？";
    /**
     * 天気取得サイトのURL（地名指定用）
     */
    public static final String FORMAT_WEATHER_SITE_URL_PLACE = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s";
    /**
     * 天気取得サイトのURL（経度、緯度指定用）
     */
    public static final String FORMAT_WEATHER_SITE_URL_GPS = "http://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s";
}
