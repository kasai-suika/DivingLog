package com.ojtapp.divinglog.controller;

import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ojtapp.divinglog.util.ConversionUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class WeatherInfoReceiveAsyncTask extends AsyncTask<WeatherInfoParams, String, String[]> {
    private static final String TAG = WeatherInfoReceiveAsyncTask.class.getSimpleName();

    /**
     * コールバック設定用
     */
    @Nullable
    private WeatherInfoCallback weatherInfoCallback;

    /**
     * 天気と天気IDを持つMap
     */
    @NonNull
    private final Map<String, String> weatherMap = new HashMap<String, String>() {
        {
            put("200", "小雨と雷雨");
            put("201", "雨と雷雨");
            put("202", "大雨と雷雨");
            put("210", "光雷雨");
            put("211", "雷雨");
            put("212", "重い雷雨");
            put("221", "ぼろぼろの雷雨");
            put("230", "小雨と雷雨");
            put("231", "霧雨と雷雨");
            put("232", "重い霧雨と雷雨");
            put("300", "光強度霧雨");
            put("301", "霧雨");
            put("302", "重い強度霧雨");
            put("310", "光強度霧雨の雨");
            put("311", "霧雨の雨");
            put("312", "重い強度霧雨の雨");
            put("313", "にわかの雨と霧雨");
            put("314", "重いにわかの雨と霧雨");
            put("321", "にわか霧雨");
            put("500", "小雨");
            put("501", "適度な雨");
            put("502", "重い強度の雨");
            put("503", "非常に激しい雨");
            put("504", "極端な雨");
            put("511", "雨氷");
            put("520", "光強度のにわかの雨");
            put("521", "にわかの雨");
            put("522", "重い強度にわかの雨");
            put("531", "不規則なにわかの雨");
            put("600", "小雪");
            put("601", "雪");
            put("602", "大雪");
            put("611", "みぞれ");
            put("612", "にわかみぞれ");
            put("615", "光雨と雪");
            put("616", "雨や雪");
            put("620", "光のにわか雪");
            put("621", "にわか雪");
            put("622", "重いにわか雪");
            put("701", "ミスト");
            put("711", "煙");
            put("721", "ヘイズ");
            put("731", "砂、ほこり旋回する");
            put("741", "霧");
            put("751", "砂");
            put("761", "ほこり");
            put("762", "火山灰");
            put("771", "スコール");
            put("781", "竜巻");
            put("800", "晴天");
            put("801", "薄い雲");
            put("802", "雲");
            put("803", "曇りがち");
            put("804", "厚い雲");
        }
    };

    @Override
    protected String[] doInBackground(WeatherInfoParams... params) {
        // 天気情報サイトのURLの文字列を取得
        String urlStr = getWeatherSiteURL(params[0]);

        // URL先に接続し文字列データを取得
        String dataStr = null;
        try {
            InputStream data = getDataFromURL(urlStr);  // URL先に接続しデータを取得
            dataStr = data2String(data);                // データを文字列に変換
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 取得した文字列データをJSONに変換し、必要な情報を取得する
        String weather = " ";
        String temp = " ";
        try {
            // 天気
            assert dataStr != null;
            JSONObject rootJSON = new JSONObject(dataStr);
            JSONArray weatherJSON = rootJSON.getJSONArray("weather");
            JSONObject weatherJSON0 = weatherJSON.getJSONObject(0);
            String weatherId = weatherJSON0.getString("id");
            if (weatherMap.containsKey(weatherId)) {
                weather = weatherMap.get(weatherId);
            } else {
                weather = weatherJSON0.getString("main");
            }

            // 気温
            JSONObject mainJSON = rootJSON.getJSONObject("main");
            String kelvinTemp = mainJSON.getString("temp");
            double intTemp = Double.parseDouble(kelvinTemp) - 273.1;
            DecimalFormat df = new DecimalFormat("###.#");
            temp = df.format(intTemp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new String[]{weather, temp};
    }

    @Override
    public void onPostExecute(String[] result) {
        super.onPostExecute(result);
        String weather = result[0];
        String temp = result[1];

        if (null != weatherInfoCallback) {
            weatherInfoCallback.onSuccess(weather, temp);
        }
    }

    /**
     * URL先から取得したバイトデータを文字列に変換
     *
     * @param is URL先から取得したバイトデータ
     * @return バイトデータを文字列に変換したもの
     * @throws IOException 　文字列変換時にI/Oエラーが起こった場合にthrowsされる
     */
    private String data2String(@NonNull InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        char[] b = new char[1024];
        int line;
        while (0 <= (line = reader.read(b))) {
            sb.append(b, 0, line);
        }
        return sb.toString();
    }

    /**
     * 天気情報サイトのURLの文字列を取得
     *
     * @param params 天気取得対象の情報
     * @return 天気情報サイトのURLの文字列
     */
    private String getWeatherSiteURL(WeatherInfoParams params) {

        if (params instanceof WeatherInfoParams.PlaceName) {
            String place = ((WeatherInfoParams.PlaceName) params).place;
            return ConversionUtil.getWeatherSiteURL(place);
        } else {
            String latitude = ((WeatherInfoParams.GeographicCoordinates) params).latitude;
            String longitude = ((WeatherInfoParams.GeographicCoordinates) params).longitude;
            return ConversionUtil.getWeatherSiteURL(latitude, longitude);
        }
    }

    /**
     * URL先に接続しデータを取得
     *
     * @param urlStr URLの文字列
     * @return URL先のデータ
     * @throws IOException error
     */
    private InputStream getDataFromURL(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();
        return con.getInputStream();
    }

    /**
     * コールバック処理を設定
     *
     * @param weatherInfoCallback コールバックする内容
     */
    public void setWeatherInfoCallback(@Nullable WeatherInfoCallback weatherInfoCallback) {
        this.weatherInfoCallback = weatherInfoCallback;
    }

    /**
     * コールバック用インターフェイス
     */
    public interface WeatherInfoCallback {
        void onSuccess(String weather, String temp);

        void onFailure();
    }
}
