package com.example.bluetoothgatewaytool.bluetooth;


import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.example.bluetoothgatewaytool.activty.BaseActivity;

public class BluetoothServiceConnection implements ServiceConnection {

    public BluetoothService.BluetoothController controller;

    private Observer activity;
    public BluetoothServiceConnection(Observer activity){
        this.activity=activity;
    }
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        controller=(BluetoothService.BluetoothController)service;
        controller.getSubject().registerObserver(activity);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
    public void onServiceDisconnected(Observer activity){
        controller.getSubject().removeObserver(activity);
    }
}
