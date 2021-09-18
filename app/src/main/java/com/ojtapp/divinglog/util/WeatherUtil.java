package com.ojtapp.divinglog.util;

import java.util.HashMap;
import java.util.Map;

public class WeatherUtil {

    /**
     * 天気と天気コードの対応Mapを返す
     * @return 天気と天気コードの対応Map
     */
    public static Map<String, String> getWeatherMap() {
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
}
