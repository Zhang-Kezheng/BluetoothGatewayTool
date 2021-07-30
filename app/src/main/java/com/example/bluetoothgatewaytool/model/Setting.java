package com.example.bluetoothgatewaytool.model;

import org.litepal.crud.DataSupport;

/**
 * @author 章可政
 * @date 2021/7/21 15:27
 */

public class Setting extends DataSupport {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 设备号
     */
    private String deviceId;

    /**
     * wifi路由器名字
     */
    private String SSID;
    /**
     * wifi路由器密码
     */
    private String SSIDPassword;
    /**
     * 目标服务器IP
     */
    private String IP;

    /**
     * 目标服务端口
     */
    private Integer port;

    /**
     * 蓝牙终端信号强度阈值
     */
    private Integer RSSI;

    /**
     * 过滤值
     */
    private String filter_value;

    /**
     * 蓝牙接受频点
     */
    private Integer CommunicationType;

    /**
     * meshId
     */
    private String meshId;

    /**
     * 是否打开蓝牙广播
     */
    private boolean isIBeaconBroadcast;

    /**
     * 是否启用保留项
     */
    private boolean isReserved;

    /**
     * 保留项内容
     */
    private String reservedContent;
    /**
     * 是否打开蓝牙广播接收
     */
    private boolean isBluetoothAccept;

    /**
     * 协议类型
     */
    private Integer agreementType;

    /**
     * 网络类型
     */
    private Integer netWorkType;

    /**
     * 固定ip
     */
    private String fixIp;
    /**
     * 子网掩码
     */
    private String mask;
    /**
     * 网关
     */
    private String gateway;
    /**
     * 设备安装位置
     */
    private Integer scene;

    /**
     * 是否启用第六包
     */
    private boolean isEnabledSix;
    /**
     * 是否启用第七包
     */
    private boolean isEnabledSeven;
    /**
     * 是否启用第八包
     */
    private boolean isEnabledEight;
    /**
     * 是否启用第九包
     */
    private boolean isEnabledNine;
    /**
     * 是否启用第十包
     */
    private boolean isEnabledTen;
    /**
     * 第六包内容
     */
    private String sixContent;
    /**
     * 第七包内容
     */
    private String sevenContent;
    /**
     * 第八包内容
     */
    private String eightContent;
    /**
     * 第九包内容
     */
    private String nineContent;
    /**
     * 第十包内容
     */
    private String tenContent;

    public String getFilter_value() {
        return filter_value;
    }

    public void setFilter_value(String filter_value) {
        this.filter_value = filter_value;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    public String getReservedContent() {
        return reservedContent;
    }

    public void setReservedContent(String reservedContent) {
        this.reservedContent = reservedContent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public String getSSIDPassword() {
        return SSIDPassword;
    }

    public void setSSIDPassword(String SSIDPassword) {
        this.SSIDPassword = SSIDPassword;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getRSSI() {
        return RSSI;
    }

    public void setRSSI(Integer RSSI) {
        this.RSSI = RSSI;
    }

    public Integer getCommunicationType() {
        return CommunicationType;
    }

    public void setCommunicationType(Integer communicationType) {
        CommunicationType = communicationType;
    }

    public String getMeshId() {
        return meshId;
    }

    public void setMeshId(String meshId) {
        this.meshId = meshId;
    }

    public boolean isIBeaconBroadcast() {
        return isIBeaconBroadcast;
    }

    public void setIBeaconBroadcast(boolean IBeaconBroadcast) {
        isIBeaconBroadcast = IBeaconBroadcast;
    }

    public boolean isBluetoothAccept() {
        return isBluetoothAccept;
    }

    public void setBluetoothAccept(boolean bluetoothAccept) {
        isBluetoothAccept = bluetoothAccept;
    }

    public Integer getAgreementType() {
        return agreementType;
    }

    public void setAgreementType(Integer agreementType) {
        this.agreementType = agreementType;
    }

    public Integer getNetWorkType() {
        return netWorkType;
    }

    public void setNetWorkType(Integer netWorkType) {
        this.netWorkType = netWorkType;
    }

    public String getFixIp() {
        return fixIp;
    }

    public void setFixIp(String fixIp) {
        this.fixIp = fixIp;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public Integer getScene() {
        return scene;
    }

    public void setScene(Integer scene) {
        this.scene = scene;
    }

    public boolean isEnabledSix() {
        return isEnabledSix;
    }

    public void setEnabledSix(boolean enabledSix) {
        isEnabledSix = enabledSix;
    }

    public boolean isEnabledSeven() {
        return isEnabledSeven;
    }

    public void setEnabledSeven(boolean enabledSeven) {
        isEnabledSeven = enabledSeven;
    }

    public boolean isEnabledEight() {
        return isEnabledEight;
    }

    public void setEnabledEight(boolean enabledEight) {
        isEnabledEight = enabledEight;
    }

    public boolean isEnabledNine() {
        return isEnabledNine;
    }

    public void setEnabledNine(boolean enabledNine) {
        isEnabledNine = enabledNine;
    }

    public boolean isEnabledTen() {
        return isEnabledTen;
    }

    public void setEnabledTen(boolean enabledTen) {
        isEnabledTen = enabledTen;
    }

    public String getSixContent() {
        return sixContent;
    }

    public void setSixContent(String sixContent) {
        this.sixContent = sixContent;
    }

    public String getSevenContent() {
        return sevenContent;
    }

    public void setSevenContent(String sevenContent) {
        this.sevenContent = sevenContent;
    }

    public String getEightContent() {
        return eightContent;
    }

    public void setEightContent(String eightContent) {
        this.eightContent = eightContent;
    }

    public String getNineContent() {
        return nineContent;
    }

    public void setNineContent(String nineContent) {
        this.nineContent = nineContent;
    }

    public String getTenContent() {
        return tenContent;
    }

    public void setTenContent(String tenContent) {
        this.tenContent = tenContent;
    }
}

