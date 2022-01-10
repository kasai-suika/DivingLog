//package com.ojtapp.divinglog;
//
//import android.content.Context;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.test.platform.app.InstrumentationRegistry;
//
//import com.ojtapp.divinglog.appif.DivingLog;
//import com.ojtapp.divinglog.controller.DisplayAsyncTask;
//import com.ojtapp.divinglog.controller.RegisterAsyncTask;
//import com.ojtapp.divinglog.controller.UpdateAsyncTask;
//
//import org.junit.Test;
//
//import java.util.List;
//import java.util.concurrent.CountDownLatch;
//
//import static org.junit.Assert.assertEquals;
//
//public class DataBaseTest {
//    private static final int[] DIVE_NUM = {0, 1};
//    private static final int[] DEPTH_MAX = {1, 2};
//    private static final int[] DEPTH_AVE = {2, 3};
//    private static final int[] AIR_START = {3, 4};
//    private static final int[] AIR_END = {4, 5};
//    private static final int[] AIR_DIVE = {5, 6};
//    private static final int[] TEMP = {6, 7};
//    private static final int[] TEMP_WATER = {7, 8};
//    private static final int[] VISIBILITY = {8, 9};
//    private static final String[] PLACE = {"place1", "place2"};
//    private static final String[] POINT = {"point1", "point2"};
//    private static final String[] WEATHER = {"weather1", "weather2"};
//    private static final String[] MEMBER = {"member1", "member2"};
//    private static final String[] NAVI = {"navi1", "navi2"};
//    private static final String[] MEMO = {"memo1", "memo2"};
//    private static final String[] DATE = {"0000/00/01", "0000/00/02"};
//    private static final String[] TIME_START = {"00:01", "00:02"};
//    private static final String[] TIME_END = {"11:11", "11:12"};
//    private static final String[] TIME_DIVE = {"22:21", "22:22"};
//
//    // 定数のindexの値
//    private static final int INDEX0 = 0;
//    private static final int INDEX1 = 1;
//
//    final DivingLog[] divingLogs = new DivingLog[2];
//
//    // DB内のデータの場所
//    int dataPosition;
//
//
//    @Test
//    public void getDataFromDB() throws InterruptedException {
//        final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
//
//        divingLogs[0] = new DivingLog();
//        setDataToDivingLog(divingLogs[0], INDEX0);  // 指定した要素の定数値をDivingLogクラスに設定
//        setDataToDB(divingLogs[0], context);    // DBにDivingLogクラスを保存
//
//        // -----【DB】取得処理--------------
//        DisplayAsyncTask displayAsyncTask = new DisplayAsyncTask(context);
//        displayAsyncTask.setOnCallBack(new DisplayAsyncTask.DisplayCallback() {
//            @Override
//            public void onSuccess(List<DivingLog> logList) {
//                dataPosition = logList.size() - 1;  // 最後に保存したデータの場所を取得
//                divingLogs[1] = logList.get(dataPosition);  // DB内のデータを空のDivingLogクラスに設定
//                assertGetData(divingLogs[1], INDEX0);   // 指定したDivingLogクラスに指定した要素の定数値が入っているか判定
//            }
//        });
//        displayAsyncTask.execute();
//    }
//
//    @Test
//    public void updateDataInDB() throws InterruptedException {
//        final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
//
//        getDataFromDB();    // DivingLogクラスをDBに保存し取得する。divingLogs[1]にはDBに最後に保存したデータが入っている。
//        Thread.sleep(1000);
//
//        setDataToDivingLog(divingLogs[1], INDEX1);   // 指定した要素の定数値をDivingLogクラスに設定。INDEX0の定数をINDEX1に書き換える。
//
//        // -----【DB】更新処理--------------
//        UpdateAsyncTask updateAsyncTask = new UpdateAsyncTask(context);
//        updateAsyncTask.setUpdateCallback(new UpdateAsyncTask.UpdateCallback() {
//            @Override
//            public void onFailure() {
//                Log.e()
//            }
//
//            @Override
//            public void onSuccess() {
//                // -----【DB】取得処理--------------
//                DisplayAsyncTask displayAsyncTask = new DisplayAsyncTask(context);
//                displayAsyncTask.setOnCallBack(new DisplayAsyncTask.DisplayCallback() {
//                    @Override
//                    public void onSuccess(List<DivingLog> logList) {
//                        // listの最後のデータを取得
//                        divingLogs[1] = logList.get(dataPosition);  // getDataFromDB() で取得したデータの場所と同じ場所にあるデータを取得
//                        assertGetData(divingLogs[1], INDEX1);   // 指定したDivingLogクラスに指定した要素の定数値が入っているか判定
//                    }
//
//                    @Override
//                    public void onFailure() {
//
//                    }
//                });
//                displayAsyncTask.execute();
//            }
//        });
//        updateAsyncTask.execute(divingLogs[1]);
//    }
//
//    private void setDataToDB(@NonNull DivingLog divingLog, @NonNull Context context) throws InterruptedException {
//        final boolean[] flag = {false};
//        final CountDownLatch countDownLatch = new CountDownLatch(1);
//
//        // -----【DB】保存処理--------------
//        RegisterAsyncTask registerAsyncTask = new RegisterAsyncTask(context);
//        registerAsyncTask.setOnCallBack(new RegisterAsyncTask.RegisterCallback() {
//            @Override
//            public void onSuccess(Boolean result) {
//                countDownLatch.countDown();
//            }
//        });
//        registerAsyncTask.execute(divingLog);
//
//        countDownLatch.await();
//    }
//
//    private void assertGetData(@NonNull DivingLog divingLog, int index) {
//        assertEquals(DIVE_NUM[index], divingLog.getDivingNumber());
//        assertEquals(DEPTH_MAX[index], divingLog.getDepthMax());
//        assertEquals(DEPTH_AVE[index], divingLog.getDepthAve());
//        assertEquals(AIR_START[index], divingLog.getAirStart());
//        assertEquals(AIR_END[index], divingLog.getAirEnd());
//        assertEquals(AIR_DIVE[index], divingLog.getAirDive());
//        assertEquals(TEMP[index], divingLog.getTemp());
//        assertEquals(TEMP_WATER[index], divingLog.getTempWater());
//        assertEquals(VISIBILITY[index], divingLog.getVisibility());
//        assertEquals(PLACE[index], divingLog.getPlace());
//        assertEquals(POINT[index], divingLog.getPoint());
//        assertEquals(MEMBER[index], divingLog.getMember());
//        assertEquals(NAVI[index], divingLog.getMemberNavigate());
//        assertEquals(MEMO[index], divingLog.getMemo());
//        assertEquals(DATE[index], divingLog.getDate());
//        assertEquals(TIME_START[index], divingLog.getTimeStart());
//        assertEquals(TIME_END[index], divingLog.getTimeEnd());
//        assertEquals(TIME_DIVE[index], divingLog.getTimeDive());
//    }
//
//    private void setDataToDivingLog(@NonNull DivingLog divingLog, int index) {
//        divingLog.setDiveNumber(DIVE_NUM[index]);
//        divingLog.setPlace(PLACE[index]);
//        divingLog.setPoint(POINT[index]);
//        divingLog.setDepthMax(DEPTH_MAX[index]);
//        divingLog.setDepthAve(DEPTH_AVE[index]);
//        divingLog.setAirStart(AIR_START[index]);
//        divingLog.setAirEnd(AIR_END[index]);
//        divingLog.setAirDive(AIR_DIVE[index]);
//        divingLog.setWeather(WEATHER[index]);
//        divingLog.setTemp(TEMP[index]);
//        divingLog.setTempWater(TEMP_WATER[index]);
//        divingLog.setVisibility(VISIBILITY[index]);
//        divingLog.setMember(MEMBER[index]);
//        divingLog.setMemberNavigate(NAVI[index]);
//        divingLog.setMemo(MEMO[index]);
//        divingLog.setDate(DATE[index]);
//        divingLog.setTimeEnd(TIME_END[index]);
//        divingLog.setTimeStart(TIME_START[index]);
//        divingLog.setTimeDive(TIME_DIVE[index]);
//    }
//}
