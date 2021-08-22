package com.ojtapp.divinglog.controller;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.Nullable;

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
import java.util.HashMap;
import java.util.Map;

public class WeatherInfoReceiver extends AsyncTask<String, String, String> {
    private static final String TAG = WeatherInfoReceiver.class.getSimpleName();
    private static final String APIKEY = "2d40ab23ed641ab19053f5cdeaa4f2c4";
    /**
     * コールバック設定用
     */
    @Nullable
    private WeatherInfoCallback weatherInfoCallback;

    @Override
    protected String doInBackground(String... params) {
        String cityName = params[0];
        String urlStr = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + APIKEY;
        String result = " ";

        HttpURLConnection con = null;
        InputStream is = null;

        try {
            URL url = new URL(urlStr);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            is = con.getInputStream();
            result = is2String(is);
        } catch (IOException ex) {
            Log.e(TAG, "error = " + ex);
        } finally {
            if (con != null) {
                con.disconnect();
            }

            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    Log.e(TAG, "error = " + ex);
                }
            }
        }
        return result;
    }

    @Override
    public void onPostExecute(String result) {
        super.onPostExecute(result);
        String weather = "";
        String temp = "";

        try {
            // 天気
            JSONObject rootJSON = new JSONObject(result);
            JSONArray weatherJSON = rootJSON.getJSONArray("weather");
            JSONObject weatherJSON0 = weatherJSON.getJSONObject(0);
            String weatherId = weatherJSON0.getString("id");
            Map<String, String> weatherMap = getWeatherMap();
            if (weatherMap.containsKey(weatherId)) {
                weather = weatherMap.get(weatherId);
            } else {
                weather = weatherJSON0.getString("main");
            }

            // 気温
            JSONObject mainJSON = rootJSON.getJSONObject("main");
            String kelvinTemp = mainJSON.getString("temp");
            int intTemp = (int) (Float.parseFloat(kelvinTemp) - 273.15);
            temp = String.valueOf(intTemp); //TODO:小数点ありきで保存できると〇。INTではなくTEXTでDB保存する？

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (null != weatherInfoCallback) {
            if (null != result) {
                weatherInfoCallback.onSuccess(weather, temp);
            } else {
                weatherInfoCallback.onFailure();
            }
        }
    }

    private String is2String(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        StringBuffer sb = new StringBuffer();
        char[] b = new char[1024];
        int line;
        while (0 <= (line = reader.read(b))) {
            sb.append(b, 0, line);
        }
        return sb.toString();
    }

    private Map<String, String> getWeatherMap() {
        Map<String, String> weatherMap = new HashMap<String, String>();
        weatherMap.put("200", "小雨と雷雨");
        weatherMap.put("201", "雨と雷雨");
        weatherMap.put("202", "大雨と雷雨");
        weatherMap.put("210", "光雷雨");
        weatherMap.put("211", "雷雨");
        weatherMap.put("212", "重い雷雨");
        weatherMap.put("221", "ぼろぼろの雷雨");
        weatherMap.put("230", "小雨と雷雨");
        weatherMap.put("231", "霧雨と雷雨");
        weatherMap.put("232", "重い霧雨と雷雨");
        weatherMap.put("300", "光強度霧雨");
        weatherMap.put("301", "霧雨");
        weatherMap.put("302", "重い強度霧雨");
        weatherMap.put("310", "光強度霧雨の雨");
        weatherMap.put("311", "霧雨の雨");
        weatherMap.put("312", "重い強度霧雨の雨");
        weatherMap.put("313", "にわかの雨と霧雨");
        weatherMap.put("314", "重いにわかの雨と霧雨");
        weatherMap.put("321", "にわか霧雨");
        weatherMap.put("500", "小雨");
        weatherMap.put("501", "適度な雨");
        weatherMap.put("502", "重い強度の雨");
        weatherMap.put("503", "非常に激しい雨");
        weatherMap.put("504", "極端な雨");
        weatherMap.put("511", "雨氷");
        weatherMap.put("520", "光強度のにわかの雨");
        weatherMap.put("521", "にわかの雨");
        weatherMap.put("522", "重い強度にわかの雨");
        weatherMap.put("531", "不規則なにわかの雨");
        weatherMap.put("600", "小雪");
        weatherMap.put("601", "雪");
        weatherMap.put("602", "大雪");
        weatherMap.put("611", "みぞれ");
        weatherMap.put("612", "にわかみぞれ");
        weatherMap.put("615", "光雨と雪");
        weatherMap.put("616", "雨や雪");
        weatherMap.put("620", "光のにわか雪");
        weatherMap.put("621", "にわか雪");
        weatherMap.put("622", "重いにわか雪");
        weatherMap.put("701", "ミスト");
        weatherMap.put("711", "煙");
        weatherMap.put("721", "ヘイズ");
        weatherMap.put("731", "砂、ほこり旋回する");
        weatherMap.put("741", "霧");
        weatherMap.put("751", "砂");
        weatherMap.put("761", "ほこり");
        weatherMap.put("762", "火山灰");
        weatherMap.put("771", "スコール");
        weatherMap.put("781", "竜巻");
        weatherMap.put("800", "晴天");
        weatherMap.put("801", "薄い雲");
        weatherMap.put("802", "雲");
        weatherMap.put("803", "曇りがち");
        weatherMap.put("804", "厚い雲");

        return weatherMap;
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
