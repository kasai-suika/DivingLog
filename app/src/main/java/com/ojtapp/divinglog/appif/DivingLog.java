package com.ojtapp.divinglog.appif;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class DivingLog implements Serializable {
    private int logId;
    private int diveNumber;
    private String place;
    private String point;
    private int depthMax;
    private int depthAve;
    private int airStart;
    private int airEnd;
    private int airDive;
    private String weather;
    private double temp;
    private double tempWater;
    private int visibility;
    private String memberNavigate;
    private String member;
    private String memo;
    private String date;
    private String timeStart;
    private String timeEnd;
    private String timeDive;
    private String pictureUri;

    /**
     * コンストラクタ
     */
    public DivingLog() {
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getDivingNumber() {
        return diveNumber;
    }

    public void setDiveNumber(int diveNumber) {
        this.diveNumber = diveNumber;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(@Nullable String place) {
        this.place = place;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(@Nullable String point) {
        this.point = point;
    }

    public int getDepthMax() {
        return depthMax;
    }

    public void setDepthMax(int depthMax) {
        this.depthMax = depthMax;
    }

    public int getDepthAve() {
        return depthAve;
    }

    public void setDepthAve(int depthAve) {
        this.depthAve = depthAve;
    }

    public int getAirStart() {
        return airStart;
    }

    public void setAirStart(int airStart) {
        this.airStart = airStart;
    }

    public int getAirEnd() {
        return airEnd;
    }

    public void setAirEnd(int airEnd) {
        this.airEnd = airEnd;
    }

    public int getAirDive() {
        return airDive;
    }

    public void setAirDive(int airDive) {
        this.airDive = airDive;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(@Nullable String weather) {
        this.weather = weather;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getTempWater() {
        return tempWater;
    }

    public void setTempWater(double tempWater) {
        this.tempWater = tempWater;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public String getMemberNavigate() {
        return memberNavigate;
    }

    public void setMemberNavigate(@Nullable String memberNavigate) {
        this.memberNavigate = memberNavigate;
    }

    public String getMember() {
        return member;
    }

    public void setMember(@Nullable String member) {
        this.member = member;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(@Nullable String memo) {
        this.memo = memo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(@Nullable String date) {
        this.date = date;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(@Nullable String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(@Nullable String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getTimeDive() {
        return timeDive;
    }

    public void setTimeDive(@Nullable String timeDive) {
        this.timeDive = timeDive;
    }

    public String getPictureUri() {
        return pictureUri;
    }

    public void setPictureUri(@Nullable String pictureUri) {
        this.pictureUri = pictureUri;
    }
}
