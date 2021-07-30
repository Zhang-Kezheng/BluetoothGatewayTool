package com.example.bluetoothgatewaytool.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author 章可政
 * @date 2021/7/22 19:54
 */
public class ByteUtil {
    private static final char HexCharArr[] = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
    private static final String HexStr = "0123456789ABCDEF";
    public static boolean equals(byte[] src,byte[] target){
        if (src.length!=target.length){
            return false;
        }
        for (int i = 0; i < src.length; i++) {
            if (src[i]!=target[i]){
                return false;
            }
        }
        return true;
    }

    public static byte[] intToBytes(int src){
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(src);
        byte[] target=new byte[4];
        buffer.readBytes(target);
        buffer.release();
        return target;
    }


    public static byte[] stringToBytes(String src){
        String[] split = src.split("\\.");
        if (split.length != 4){
            return new byte[4];
        }
        byte[] ipBytes=new byte[4];
        ipBytes[0]= (byte) Integer.parseInt(split[0]);
        ipBytes[1]= (byte) Integer.parseInt(split[1]);
        ipBytes[2]= (byte) Integer.parseInt(split[2]);
        ipBytes[3]= (byte) Integer.parseInt(split[3]);
        return ipBytes;
    }
    public static String byteArrToHex(byte[] btArr) {
        char[] strArr = new char[btArr.length * 3];
        int i = 0;
        for (byte bt : btArr) {
            strArr[i++] = HexCharArr[bt>>>4 & 0xf];
            strArr[i++] = HexCharArr[bt & 0xf];
            strArr[i++]=' ';
        }
        return new String(strArr);
    }
    public static byte[] hexToByteArr(String hexStr) {
        char[] charArr = hexStr.toCharArray();
        byte btArr[] = new byte[charArr.length / 2];
        int index = 0;
        for (int i = 0; i < charArr.length; i++) {
            int highBit = HexStr.indexOf(charArr[i]);
            int lowBit = HexStr.indexOf(charArr[++i]);
            btArr[index] = (byte) (highBit << 4 | lowBit);
            index++;
        }
        return btArr;
    }
}
