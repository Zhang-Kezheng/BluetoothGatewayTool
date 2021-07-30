package com.example.bluetoothgatewaytool.bluetooth;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.*;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.clj.fastble.BleManager;
import com.example.bluetoothgatewaytool.R;
import com.example.bluetoothgatewaytool.activty.Constant;
import com.example.bluetoothgatewaytool.util.ByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author 章可政
 * @date 2021/7/25 19:57
 */
public class BluetoothService extends Service implements Subject {

    private BluetoothController controller;

    private List<Observer> observers = new ArrayList<>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        controller = new BluetoothController();
        return controller;
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(int command) {

        for (Observer observer : observers) {
            observer.update(command);
        }
    }

    public class BluetoothController extends Binder {


        public static final int manufacturerID = 0xc7;

        public boolean isStop = true;

        private final String TAG = "main";

        private final BluetoothLeAdvertiser bluetoothLeAdvertiser;

        private final BluetoothLeScanner bluetoothLeScanner;

        private ScanSettings scanSettings;
        private BluetoothController() {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            bluetoothLeAdvertiser = bluetoothAdapter.getBluetoothLeAdvertiser();
            bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
            ScanSettings.Builder builder = new ScanSettings.Builder();
            builder.setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY);
            scanSettings=builder.build();
        }

        public Subject getSubject() {
            return BluetoothService.this;
        }

        public void enable() {
            BleManager.getInstance().enableBluetooth();
        }

        public boolean isOpen() {
            return BleManager.getInstance().isBlueEnable();
        }

        private AdvertiseData buildAdvertiseData(byte[] sendDatas) {
            AdvertiseData.Builder dataBuilder = new AdvertiseData.Builder();
            dataBuilder.setIncludeDeviceName(false); // 是否包含设备名称
            dataBuilder.setIncludeTxPowerLevel(false);
            dataBuilder.addManufacturerData(manufacturerID, sendDatas);
            return dataBuilder.build();
        }


        private AdvertiseSettings buildAdvertiseSettings() {
            AdvertiseSettings.Builder settingsBuilder = new AdvertiseSettings.Builder();
            settingsBuilder.setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY);
            settingsBuilder.setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH);
            settingsBuilder.setConnectable(true);
            settingsBuilder.setTimeout(0);
            return settingsBuilder.build();
        }



        public void startAdvertising(List<byte[]> bytes) {
            if (!isStop) {
                Log.e(TAG, "广播正在执行，请先关闭广播，调用stopAdvertising,再来打开广播");
                return;
            }
            isStop = false;
            new Thread(() -> {
                while (!isStop) {
                    for (byte[] data : bytes) {
                        bluetoothLeAdvertiser.startAdvertising(buildAdvertiseSettings(), buildAdvertiseData(data), callback);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        bluetoothLeAdvertiser.stopAdvertising(callback);
                    }
                }
            }).start();
        }
        public void stopAdvertising() {
            isStop=true;
        }

        public void startScanLe() {
            if (!isOpen()) {
                enable();
            }
            bluetoothLeScanner.startScan(null, scanSettings,scanCallback);
            System.out.println("广播扫描开启");
        }

        public void stopScanLe() {
            bluetoothLeScanner.stopScan(scanCallback);
            System.out.println("广播扫描关闭");
        }

        public void handleScanRecord(BluetoothData bluetoothData) {
            byte[] header = bluetoothData.getHeader();
            if (Arrays.equals(header, Constant.LOGIN_HEADER)) {

                if (bluetoothData.getData()[0] == 0 && bluetoothData.getData()[1] == 0) {
                    notifyObservers(0);
                } else {
                    notifyObservers(1);
                }
            } else if (Arrays.equals(header, Constant.CONFIG_HEADER)) {
                notifyObservers(2);
            } else if (Arrays.equals(header, Constant.UPDATE_HEADER)) {
                notifyObservers(3);
            }
        }

        private final AdvertiseCallback callback = new AdvertiseCallback() {
            @Override
            public void onStartSuccess(AdvertiseSettings settingsInEffect) {
                System.out.println("蓝牙广播开启成功");
            }

            @Override
            public void onStartFailure(int errorCode) {
                System.out.println("蓝牙广播开启失败" + errorCode);
            }
        };

        private final AdvertisingSetCallback advertisingSetCallback=new AdvertisingSetCallback() {
            @Override
            public void onAdvertisingSetStarted(AdvertisingSet advertisingSet, int txPower, int status) {
                super.onAdvertisingSetStarted(advertisingSet, txPower, status);
            }
        };
        private final ScanCallback scanCallback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                byte[] manufacturerSpecificData = result.getScanRecord().getManufacturerSpecificData(manufacturerID);
                if (manufacturerSpecificData != null) {
                    isStop=true;
                    System.out.println(ByteUtil.byteArrToHex(manufacturerSpecificData));
                    ByteBuf buffer = Unpooled.copiedBuffer(manufacturerSpecificData);
                    buffer.skipBytes(2);
                    byte[] deviceId = new byte[6];
                    buffer.readBytes(deviceId);
                    BluetoothData bluetoothData = new BluetoothData();
                    bluetoothData.setDeviceID(deviceId);
                    byte[] header = new byte[2];
                    buffer.readBytes(header);
                    bluetoothData.setHeader(header);
                    bluetoothData.setNumber(buffer.readByte());
                    byte[] data = new byte[buffer.readableBytes()];
                    buffer.readBytes(data);
                    bluetoothData.setData(data);
                    handleScanRecord(bluetoothData);
                }
            }

            @Override
            public void onScanFailed(int errorCode) {
                System.out.println("广播扫描开启失败"+errorCode);
                super.onScanFailed(errorCode);
            }
        };
    }
}
