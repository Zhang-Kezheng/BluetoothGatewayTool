package com.example.bluetoothgatewaytool.activty;

/**
 * @author 章可政
 * @date 2021/7/20 21:11
 */
public class Constant {
    public static final int REQ_PERM_CAMERA = 1;
    public static final String INTENT_EXTRA_KEY_QR_SCAN = "SCAN_RESULT";
    public static final int REQ_QR_CODE = 3;
    public static final int ADD_SCENE = 3;
    public static final byte[] LOGIN_HEADER={(byte) 0xFF, 0x06};
    public static final byte[] CONFIG_HEADER={(byte) 0xFF, 0x04};
    public static final byte[] UPDATE_HEADER={(byte) 0xFF, 0x05};
}
