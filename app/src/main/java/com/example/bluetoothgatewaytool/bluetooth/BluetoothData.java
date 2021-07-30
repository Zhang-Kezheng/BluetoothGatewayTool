package com.example.bluetoothgatewaytool.bluetooth;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * @author 章可政
 * @date 2021/7/22 12:10
 */

public class BluetoothData {
    private byte[] head={(byte) 0xF0,0x1E};
    private byte[] deviceID={0x01,0x02,0x03,0x04,0x05,0x06};
    private byte[] header;
    private byte number;
    private byte[] data;

    public byte[] getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(byte[] deviceID) {
        this.deviceID = deviceID;
    }

    public byte[] getHeader() {
        return header;
    }

    public void setHeader(byte[] header) {
        this.header = header;
    }

    public byte getNumber() {
        return number;
    }

    public void setNumber(byte number) {
        this.number = number;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public static BluetoothData getBluetoothData(byte[] header,byte number,byte[] data){
        BluetoothData bluetoothData=new BluetoothData();
        bluetoothData.setHeader(header);
        bluetoothData.setNumber(number);
        byte[] bytes=new byte[13];
        System.arraycopy(data, 0, bytes, 0, data.length);
        bluetoothData.setData(bytes);
        return bluetoothData;
    }
    public static BluetoothData getBluetoothData(byte[] deviceID,byte[] header,byte number,byte[] data){
        BluetoothData bluetoothData=new BluetoothData();
        bluetoothData.setDeviceID(deviceID);
        bluetoothData.setHeader(header);
        bluetoothData.setNumber(number);
        byte[] bytes=new byte[13];
        System.arraycopy(data, 0, bytes, 0, data.length);
        bluetoothData.setData(bytes);
        return bluetoothData;
    }
    /**
     * 解析数据
     * @return
     */
    public byte[] parseData(){
        ByteBuf buffer=Unpooled.buffer();
        buffer.writeBytes(head);
        buffer.writeBytes(deviceID);
        buffer.writeBytes(header);
        buffer.writeByte(number);
        buffer.writeBytes(data);
        int length = buffer.readableBytes();
        byte[] res=new byte[length];
        buffer.readBytes(res);
        return res;
    }
}
