package com.example.bluetoothgatewaytool.bluetooth;

import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseSettings;

/**
 * @author 章可政
 * @date 2021/7/27 18:14
 */
public class CustomCallback extends AdvertiseCallback {
    @Override
    public void onStartSuccess(AdvertiseSettings settingsInEffect) {
        System.out.println("蓝牙广播开启成功");
    }

    @Override
    public void onStartFailure(int errorCode) {
        System.out.println("蓝牙广播开启失败" + errorCode);
    }
}
