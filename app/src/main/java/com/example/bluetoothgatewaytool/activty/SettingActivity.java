package com.example.bluetoothgatewaytool.activty;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.bluetoothgatewaytool.R;
import com.example.bluetoothgatewaytool.adapter.CustomBaseAdapter;
import com.example.bluetoothgatewaytool.bluetooth.BluetoothData;
import com.example.bluetoothgatewaytool.dialog.FixIpDialog;
import com.example.bluetoothgatewaytool.model.*;
import com.example.bluetoothgatewaytool.util.AnimatorUtil;
import com.example.bluetoothgatewaytool.util.ByteUtil;
import com.example.bluetoothgatewaytool.util.StringUtils;
import com.king.zxing.CaptureActivity;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.example.bluetoothgatewaytool.activty.Constant.*;

public class SettingActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    /**
     * 场景下拉列表箭头图片
     */
    private ImageView scene_spinner_image;

    /**
     * dhcp_ip下拉列表箭头图片
     */
    private ImageView dhcp_ip_spinner_image;

    /**
     * communication_type下拉列表箭头图片
     */
    private ImageView communication_type_spinner_image;

    /**
     * 协议下拉列表箭头图片
     */
    private ImageView agreement_type_spinner_image;

    /**
     * 场景名字
     */
    private TextView scene_name;

    /**
     * dhcp_ip名字
     */
    private TextView dhcp_ip_name;

    /**
     * communication_type名字
     */
    private TextView communication_type_name;

    /**
     * 协议类型名字
     */
    private TextView agreement_type_name;

    /**
     * 场景位置卡片
     */
    private CardView setting_scene_card;

    /**
     * dhcp_ip卡片
     */
    private CardView setting_dhcp_ip_card;

    /**
     * 协议类型卡片
     */
    private CardView setting_agreement_type_card;

    /**
     * communication_type布局
     */
    private ConstraintLayout communication_type_layout;

    /**
     * 二维码扫码按钮
     */
    private ImageView scan_code;


    /**
     * 跳转密码设置页面按钮
     */
    private ImageView setting_password;

    /**
     * dhcp下拉列表
     */
    private ListPopupWindow dhcp_ip_popup;

    /**
     * 场景下拉列表
     */
    private ListPopupWindow scene_popup;

    /**
     * communication_type下拉列表
     */
    private ListPopupWindow communication_type_popup;
    /**
     * communication_type下拉列表
     */
    private ListPopupWindow agreement_type_popup;
    /**
     * 数据
     */
    private static Setting setting;
    /**
     * 设备id
     */
    private EditText id;
    /**
     * wifi路由器
     */
    private EditText ssid;
    /**
     * wifi路由器密码
     */
    private EditText ssid_passowrd;
    /**
     * 目前主机IP
     */
    private EditText ip;
    /**
     * 目标服务器端口
     */
    private EditText port;
    /**
     * 蓝牙终端信号强度阈值
     */
    private EditText rssi;

    private EditText filter_value;
    /**
     * mesh_id
     */
    private EditText mesh_id;

    /**
     * 保存按钮
     */
    private TextView save;

    /**
     * 配置按钮
     */
    private Button config_button;

    private CheckBox iBeacon;

    private CheckBox bluetooth_accept;

    private CheckBox reserved_item_box;

    private EditText reserved_item_edittext;

    private CheckBox six_box;

    private CheckBox seven_box;

    private CheckBox eight_box;

    private CheckBox nine_box;

    private CheckBox ten_box;

    private EditText six_content;

    private EditText seven_content;

    private EditText eight_content;

    private EditText nine_content;

    private EditText ten_content;

    private CustomBaseAdapter<BaseModel> communicationTypeAdapter;

    private CustomBaseAdapter<Scene> sceneAdapter;

    private CustomBaseAdapter<BaseModel> dhcpAdapter;

    private CustomBaseAdapter<BaseModel> agreementAdapter;

    private List<Scene> scenes;

    private final List<BaseModel> dhcpModels = new ArrayList<>();

    private final List<BaseModel> communicationTypeModel = new ArrayList<>();

    private final List<BaseModel> agreementModels = new ArrayList<>();

    private boolean isAccept;
    /**
     * 设置布局文件
     */
    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_setting);
    }

    /**
     * 在实例化控件之前的逻辑操作
     */
    @Override
    public void beforeInitView() {
        scenes = Scene.findAll(Scene.class);
        Scene scene = new Scene();
        scene.setName("+  添加");
        scenes.add(scene);
    }

    public static Setting getSetting() {
        if (setting == null) {
            setting = Setting.find(Setting.class, 1);
        }
        if (setting == null) {
            setting = new Setting();
        }
        return setting;
    }

    /**
     * 实例化控件
     */
    @Override
    public void initView() {

        dhcp_ip_spinner_image = findViewById(R.id.dhcp_ip_spinner_image);

        scene_spinner_image = findViewById(R.id.scene_spinner_image);

        communication_type_spinner_image = findViewById(R.id.communication_type_spinner_image);

        agreement_type_spinner_image = findViewById(R.id.agreement_type_spinner_image);

        setting_scene_card = findViewById(R.id.setting_scene_card);

        setting_dhcp_ip_card = findViewById(R.id.setting_dhcp_ip_card);

        setting_agreement_type_card = findViewById(R.id.setting_agreement_type_card);

        communication_type_layout = findViewById(R.id.communication_type_layout);

        scan_code = findViewById(R.id.scan_code);

        setting_password = findViewById(R.id.setting_password);

        scene_name = findViewById(R.id.scene_name);

        dhcp_ip_name = findViewById(R.id.dhcp_ip_name);

        communication_type_name = findViewById(R.id.communication_type_name);

        agreement_type_name = findViewById(R.id.agreement_type_name);

        id = findViewById(R.id.id);

        ssid = findViewById(R.id.ssid);

        ssid_passowrd = findViewById(R.id.ssid_passowrd);

        ip = findViewById(R.id.ip);

        port = findViewById(R.id.port);

        rssi = findViewById(R.id.rssi);

        filter_value=findViewById(R.id.filter_value);

        mesh_id = findViewById(R.id.mesh_id);

        save = findViewById(R.id.save);

        iBeacon = findViewById(R.id.iBeacon);

        bluetooth_accept = findViewById(R.id.bluetooth_accept);

        reserved_item_box=findViewById(R.id.reserved_item_check_box);

        reserved_item_edittext=findViewById(R.id.reserved_item_edittext);

        six_box = findViewById(R.id.six_box);

        seven_box = findViewById(R.id.seven_box);

        eight_box = findViewById(R.id.eight_box);

        nine_box = findViewById(R.id.nine_box);

        ten_box = findViewById(R.id.ten_box);

        six_content = findViewById(R.id.six_content);

        seven_content = findViewById(R.id.seven_content);

        eight_content = findViewById(R.id.eight_content);

        nine_content = findViewById(R.id.nine_content);

        ten_content = findViewById(R.id.ten_content);

        config_button = findViewById(R.id.config_button);
    }

    /**
     * 实例化控件之后的操作
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void afterInitView() {
        setting_dhcp_ip_card.setOnClickListener(this);
        setting_scene_card.setOnClickListener(this);
        setting_agreement_type_card.setOnClickListener(this);
        communication_type_layout.setOnClickListener(this);
        scan_code.setOnClickListener(this);
        setting_password.setOnClickListener(this);
        iBeacon.setOnCheckedChangeListener(this);
        bluetooth_accept.setOnCheckedChangeListener(this);
        reserved_item_box.setOnCheckedChangeListener(this);
        six_box.setOnCheckedChangeListener(this);
        seven_box.setOnCheckedChangeListener(this);
        eight_box.setOnCheckedChangeListener(this);
        nine_box.setOnCheckedChangeListener(this);
        ten_box.setOnCheckedChangeListener(this);
        save.setOnClickListener(this);
        config_button.setOnClickListener(this);
        initScene();
        initDHCPIP();
        initCommunicationType();
        initAgreementType();
//        initIpPopWindow();
        setting = Setting.find(Setting.class, 1);
        if (setting != null) {
            id.setText(setting.getDeviceId() + "");
            ssid.setText(setting.getSSID());
            ssid_passowrd.setText(setting.getSSID());
            ip.setText(setting.getIP());
            port.setText(setting.getPort() + "");
            rssi.setText(setting.getRSSI() + "");
            mesh_id.setText(setting.getMeshId());
            filter_value.setText(setting.getFilter_value());
            reserved_item_box.setChecked(setting.isReserved());
            reserved_item_edittext.setText(setting.getReservedContent());
            iBeacon.setChecked(setting.isIBeaconBroadcast());
            bluetooth_accept.setChecked(setting.isBluetoothAccept());
            six_box.setChecked(setting.isEnabledSix());
            seven_box.setChecked(setting.isEnabledSeven());
            eight_box.setChecked(setting.isEnabledEight());
            nine_box.setChecked(setting.isEnabledNine());
            ten_box.setChecked(setting.isEnabledTen());
            six_content.setText(setting.getSixContent());
            seven_content.setText(setting.getSevenContent());
            eight_content.setText(setting.getEightContent());
            nine_content.setText(setting.getNineContent());
            ten_content.setText(setting.getTenContent());
            communicationTypeAdapter.setIndex(setting.getCommunicationType());
            sceneAdapter.setIndex(setting.getScene());
            agreementAdapter.setIndex(setting.getAgreementType());
            dhcpAdapter.setIndex(setting.getNetWorkType());
            communication_type_name.setText(communicationTypeModel.get(setting.getCommunicationType()).getName());
            agreement_type_name.setText(agreementModels.get(setting.getAgreementType()).getName());
            dhcp_ip_name.setText(dhcpModels.get(setting.getNetWorkType()).getName());
            scene_name.setText(scenes.get(setting.getScene()).getAreaName());
        }
        if (setting == null) {
            setting = new Setting();
        }
        createLoadingDialog("正在设置请稍后");
    }

    /**
     * onClick方法的封装
     *
     * @param v
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClickEvent(View v) {
        ObjectAnimator build;
        switch (v.getId()) {
            case R.id.setting_dhcp_ip_card:
                dhcp_ip_popup.show();
                build = AnimatorUtil.build(dhcp_ip_spinner_image, -90f);
                build.start();
                break;
            case R.id.setting_scene_card:
                scene_popup.show();
                build = AnimatorUtil.build(scene_spinner_image, -90f);
                build.start();
                break;
            case R.id.communication_type_layout:
                communication_type_popup.show();
                build = AnimatorUtil.build(communication_type_spinner_image, -90f);
                build.start();
                break;
            case R.id.setting_agreement_type_card:
                agreement_type_popup.show();
                build = AnimatorUtil.build(agreement_type_spinner_image, -90f);
                build.start();
                break;
            case R.id.scan_code:
                startQrCode();
                break;
            case R.id.setting_password:
                startActivity(PasswordActivity.class);
                break;
            case R.id.save:
                boolean data = getData();
                if (data) {
                    setting.save();
                    createDialog(R.drawable.message, "保存成功");
                }
                break;
            case R.id.config_button:
                if (getData()) {
                    List<byte[]> map=new ArrayList<>();
                    map.add(getOneData().parseData());
                    map.add(getTwoData().parseData());
                    map.add(getThreeData().parseData());
                    map.add(getFourData().parseData());
                    map.add(getFiveData().parseData());
                    byte[] reservedData6 = getReservedData(setting.isEnabledSix(), setting.getSixContent(), (byte) 0x06);
                    byte[] reservedData7 = getReservedData(setting.isEnabledSeven(), setting.getSevenContent(), (byte) 0x07);
                    byte[] reservedData8 = getReservedData(setting.isEnabledEight(), setting.getEightContent(), (byte) 0x08);
                    byte[] reservedData9 = getReservedData(setting.isEnabledNine(), setting.getNineContent(), (byte) 0x09);
                    byte[] reservedData10 = getReservedData(setting.isEnabledTen(), setting.getTenContent(), (byte) 0x0A);
                    if (reservedData6!=null){
                        map.add(reservedData6);
                    }
                    if (reservedData7!=null){
                        map.add(reservedData7);
                    }
                    if (reservedData8!=null){
                        map.add(reservedData8);
                    }
                    if (reservedData9!=null){
                        map.add(reservedData9);
                    }
                    if (reservedData10!=null){
                        map.add(reservedData10);
                    }
                    connection.controller.startScanLe();
                    connection.controller.startAdvertising(map);
                    loadingDialog.show();
                    handle.postDelayed(() -> {
                        connection.controller.stopAdvertising();
                        connection.controller.stopScanLe();
                        if (isAccept) {
                            isAccept = false;
                        } else {
                            loadingDialog.dismiss();
                            createDialog(R.drawable.error, "未收到回复");
                        }
                    }, 10000);
                }
                break;
            default:
                break;
        }
    }

    private byte[] getReservedData(boolean enable,String content,byte number){
        if (enable){
            ByteBuf byteBuf=Unpooled.buffer();
            byte[] bytes1 = new byte[13];
            if (!content.equals("")){
                String[] s = content.split(" ");
                for (int i = 0; i < s.length; i++) {
                    bytes1[i]= (byte) Integer.parseInt(s[i],16);
                }

            }
            byteBuf.writeBytes(bytes1);
            byte[] bytes=new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            return BluetoothData.getBluetoothData(getDeviceIdBytes(), CONFIG_HEADER, number, bytes).parseData();
        }
        return null;
    }
    private boolean getData() {
        String id_string = id.getText().toString();
        String ssid_string = ssid.getText().toString();
        String ssid_passowrd_string = ssid_passowrd.getText().toString();
        String ip_string = ip.getText().toString();
        String port_string = port.getText().toString();
        String rssi_string = rssi.getText().toString();
        String mesh_id_string = mesh_id.getText().toString();
        String filter_value_string = filter_value.getText().toString();
        String agreement_type_name_string = agreement_type_name.getText().toString();
        String dhcp_ip_name_string = dhcp_ip_name.getText().toString();
        String reserved_item_string = reserved_item_edittext.getText().toString();
        String six_content_string = six_content.getText().toString();
        String seven_content_string = seven_content.getText().toString();
        String eight_content_string = eight_content.getText().toString();
        String nine_content_string = nine_content.getText().toString();
        String ten_content_string = ten_content.getText().toString();
        if (!filter_value_string.equals("")){
            if (Integer.parseInt(filter_value_string)<0||Integer.parseInt(filter_value_string)>65535){
                showToast("输入的数字不正确", Toast.LENGTH_SHORT);
                return false;
            }
        }
        if (StringUtils.isEmpty(agreement_type_name_string)) {
            showToast("协议类型不能为空", Toast.LENGTH_SHORT);
            return false;
        }
        if (StringUtils.isEmpty(dhcp_ip_name_string)) {
            showToast("网络类型不能为空", Toast.LENGTH_SHORT);
            return false;
        }
        if (check(mesh_id_string, "mesh id内容格式错误", 6)){
            return false;
        }
        if (check(reserved_item_string, "保留项内容格式错误", 6)){
            return false;
        }
        if (check(six_content_string, "第六包内容格式错误", 13)){
            return false;
        }
        if (check(seven_content_string, "第七包内容格式错误", 13)){
            return false;
        }
        if (check(eight_content_string, "第八包内容格式错误", 13)){
            return false;
        }
        if (check(nine_content_string, "第九包内容格式错误", 13)){
            return false;
        }
        if (check(ten_content_string, "第十包内容格式错误", 13)){
            return false;
        }
        setting.setDeviceId(id_string);
        setting.setSSID(ssid_string);
        setting.setSSIDPassword(ssid_passowrd_string);
        setting.setIP(ip_string);
        setting.setFilter_value(filter_value_string);
        setting.setReservedContent(reserved_item_string);
        if (StringUtils.isEmpty(port_string)){
            setting.setPort(null);
        }else {
            setting.setPort(Integer.parseInt(port_string));
        }
        if (StringUtils.isEmpty(rssi_string)){
            setting.setRSSI(null);
        }else {
            setting.setRSSI(Integer.parseInt(rssi_string));
        }
        setting.setMeshId(mesh_id_string);
        int agreementType;
        switch (agreement_type_name_string) {
            case "UDP":
                agreementType = 0;
                break;
            case "TCP":
                agreementType = 1;
                break;
            case "MQTT":
                agreementType = 2;
                break;
            case "HTTP":
                agreementType = 3;
                break;
            default:
                agreementType = 4;
                break;
        }
        setting.setAgreementType(agreementType);
        if (dhcp_ip_name_string.equals("DHCP")) {
            setting.setNetWorkType(0);
        } else {
            setting.setNetWorkType(1);
        }
        setting.setSixContent(six_content.getText().toString());
        setting.setSevenContent(seven_content.getText().toString());
        setting.setEightContent(eight_content.getText().toString());
        setting.setNineContent(nine_content.getText().toString());
        setting.setTenContent(ten_content.getText().toString());
        return true;
    }

    private boolean check(String data,String message,int length){
        if (!data.equals("")){
            if (data.split(" ").length>length){
                showToast(message, Toast.LENGTH_SHORT);
                return true;
            }
            for (String s : data.split(" ")) {
                if (s.length()>2){
                    showToast(message, Toast.LENGTH_SHORT);
                    return true;
                }
            }
        }
        return false;
    }
    private void translateSpinnerImage(ImageView dhcp_ip_spinner_image, float angle) {
        ObjectAnimator build = AnimatorUtil.build(dhcp_ip_spinner_image, angle);
        build.start();
    }

    private byte[] getDeviceIdBytes() {
        if (StringUtils.isEmpty(setting.getDeviceId())){
            return new byte[6];
        }
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeLong(Long.parseLong(setting.getDeviceId()));
        buffer.skipBytes(2);
        byte[] bytes = new byte[6];
        buffer.readBytes(bytes);
        return bytes;
    }

    private BluetoothData getOneData() {
        byte[] data;
        if (StringUtils.isEmpty(setting.getSSID())){
            data=new byte[1];
        }else {
            data=setting.getSSID().getBytes(StandardCharsets.UTF_8);
        }
        return BluetoothData.getBluetoothData(getDeviceIdBytes(), CONFIG_HEADER, (byte) 0x01, data);
    }

    private BluetoothData getTwoData() {
        byte[] data;
        if (StringUtils.isEmpty(setting.getSSIDPassword())){
            data=new byte[1];
        }else {
            data=setting.getSSIDPassword().getBytes(StandardCharsets.UTF_8);
        }
        return BluetoothData.getBluetoothData(getDeviceIdBytes(), CONFIG_HEADER, (byte) 0x02, data);
    }

    private BluetoothData getThreeData() {
        String ip = setting.getIP();
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(ByteUtil.stringToBytes(ip));
        if (setting.getPort()==null){
            byteBuf.writeShort(0);
        }else {
            byteBuf.writeShort(setting.getPort());
        }
        byteBuf.writeByte(0x00);
        if (setting.getCommunicationType()==null){
            byteBuf.writeByte(0);
        }else {
            byteBuf.writeByte(setting.getCommunicationType());
        }
        if (setting.getRSSI()==null){
            byteBuf.writeByte(0);
        }else {
            byteBuf.writeByte(setting.getRSSI());
        }
        byteBuf.writeBoolean(setting.isIBeaconBroadcast());
        if (setting.getFilter_value().equals("")){
            byteBuf.writeShort(0);
        }else {
            byteBuf.writeShort(Integer.parseInt(setting.getFilter_value()));
        }
        byteBuf.writeByte(setting.getAgreementType());
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        return BluetoothData.getBluetoothData(getDeviceIdBytes(), CONFIG_HEADER, (byte) 0x03, bytes);
    }

    private BluetoothData getFourData() {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeByte(setting.getNetWorkType());
        if (setting.getNetWorkType() == 1) {
            byteBuf.writeBytes(ByteUtil.stringToBytes(setting.getFixIp()));
            byteBuf.writeBytes(ByteUtil.stringToBytes(setting.getMask()));
            byteBuf.writeBytes(ByteUtil.stringToBytes(setting.getGateway()));
        }
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        return BluetoothData.getBluetoothData(getDeviceIdBytes(), CONFIG_HEADER, (byte) 0x04, bytes);
    }

    private BluetoothData getFiveData() {
        ByteBuf byteBuf = Unpooled.buffer();
        if (setting.isBluetoothAccept()) {
            byteBuf.writeByte(0x01);
        }else {
            byteBuf.writeByte(0x00);
        }
        byte[] bytes=new byte[6];
        if (!setting.getMeshId().equals("")){
            String[] mesh = setting.getMeshId().split(" ");
            for (int i = 0; i < mesh.length; i++) {
                bytes[i]= (byte) Integer.parseInt(mesh[i],16);
            }
        }
        byteBuf.writeBytes(bytes);
        byte[] b=new byte[6];
        if (setting.isReserved()&&!StringUtils.isEmpty(setting.getReservedContent())){
            String[] reservedContent = setting.getReservedContent().split(" ");
            for (int i = 0; i < reservedContent.length; i++) {
                b[i]= (byte) Integer.parseInt(reservedContent[i],16);
            }
        }
        byteBuf.writeBytes(b);
        byte[] data = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(data);
        return BluetoothData.getBluetoothData(getDeviceIdBytes(), CONFIG_HEADER, (byte) 0x05, data);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        onClickEvent(v);
    }

    /**
     * 初始化场景
     */
    private void initScene() {
        beforeInitView();
        scene_popup = new ListPopupWindow(this);
        sceneAdapter = new CustomBaseAdapter<>(this, scenes);
        initPopup(scene_popup, setting_scene_card, scene_spinner_image, R.drawable.radio_corner, sceneAdapter, SpinnerType.SCENE);
    }

    /**
     * 初始化dhcp_ip
     */
    private void initDHCPIP() {
        BaseModel baseModel = new BaseModel();
        baseModel.setName("DHCP");
        BaseModel baseModel1 = new BaseModel();
        baseModel1.setName("固定IP");
        dhcpModels.add(baseModel);
        dhcpModels.add(baseModel1);
        dhcp_ip_popup = new ListPopupWindow(this);
        dhcpAdapter = new CustomBaseAdapter<>(this, dhcpModels);
        initPopup(dhcp_ip_popup, setting_dhcp_ip_card, dhcp_ip_spinner_image, R.drawable.radio_corner, dhcpAdapter, SpinnerType.DHCPIP);
    }

    /**
     * 初始化协议类型下拉列表
     */
    private void initAgreementType() {
        BaseModel baseModel = new BaseModel();
        baseModel.setName("UDP");
        BaseModel baseModel1 = new BaseModel();
        baseModel1.setName("TCP");
        BaseModel baseModel2 = new BaseModel();
        baseModel2.setName("MQTT");
        BaseModel baseModel3 = new BaseModel();
        baseModel3.setName("HTTP");
        BaseModel baseModel4 = new BaseModel();
        baseModel4.setName("其他");
        agreementModels.add(baseModel);
        agreementModels.add(baseModel1);
        agreementModels.add(baseModel2);
        agreementModels.add(baseModel3);
        agreementModels.add(baseModel4);
        agreement_type_popup = new ListPopupWindow(this);
        agreementAdapter = new CustomBaseAdapter<>(this, agreementModels);
        initPopup(agreement_type_popup, setting_agreement_type_card, agreement_type_spinner_image, R.drawable.radio_corner, agreementAdapter, SpinnerType.AGREEMENT_TYPE);
    }

    /**
     * 初始化communication_type
     */
    private void initCommunicationType() {
        BaseModel baseModel = new BaseModel();
        baseModel.setName("37");
        BaseModel baseModel1 = new BaseModel();
        baseModel1.setName("38");
        BaseModel baseModel2 = new BaseModel();
        baseModel2.setName("39");
        communicationTypeModel.add(baseModel);
        communicationTypeModel.add(baseModel1);
        communicationTypeModel.add(baseModel2);
        communication_type_popup = new ListPopupWindow(this);
        communicationTypeAdapter = new CustomBaseAdapter<>(this, communicationTypeModel);
        initPopup(communication_type_popup, communication_type_layout, communication_type_spinner_image, R.drawable.radio_corner, communicationTypeAdapter, SpinnerType.COMMUNICATIONType);
    }

    /**
     * 初始化弹窗
     *
     * @param listPopupWindow
     * @param view
     * @param image
     * @param res
     * @param adapter
     * @param spinnerType
     */
    private void initPopup(ListPopupWindow listPopupWindow, View view, ImageView image, int res, CustomBaseAdapter<? extends BaseModel> adapter, SpinnerType spinnerType) {
        listPopupWindow.setAdapter(adapter);
        listPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        listPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        listPopupWindow.setAnchorView(view);//设置ListPopupWindow的锚点，即关联PopupWindow的显示位置和这个锚点
        listPopupWindow.setModal(true);//设置是否是模式
        listPopupWindow.setBackgroundDrawable(this.getResources().getDrawable(res));
        listPopupWindow.setOnDismissListener(() -> translateSpinnerImage(image, 0f));
        listPopupWindow.setOnItemClickListener((parent, view1, position, id) -> {
            adapter.setIndex(position);
            adapter.notifyDataSetChanged();
            switch (spinnerType) {
                case DHCPIP:
                    String name = adapter.getItem(position).getName();
                    dhcp_ip_name.setText(name);
                    if (name.equals("固定IP")) {
                        FixIpDialog.Builder builder = new FixIpDialog.Builder(this);
                        FixIpDialog created = builder.created();
                        created.show();
                    }
                    break;
                case SCENE:
                    scene_name.setText(adapter.getItem(position).getName());
                    setting.setScene(position);
                    if (scene_name.getText().equals("+  添加")) {
                        scene_name.setText("");
                        Intent intent = new Intent(this, AddSceneActivity.class);
                        startActivityForResult(intent, ADD_SCENE);
                    }
                    break;
                case COMMUNICATIONType:
                    communication_type_name.setText(adapter.getItem(position).getName());
                    setting.setCommunicationType(position);
                    break;
                case AGREEMENT_TYPE:
                    agreement_type_name.setText(adapter.getItem(position).getName());
                    setting.setAgreementType(position);
                    break;
                default:
                    break;
            }
            listPopupWindow.dismiss();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //扫描结果回调
        if (requestCode == REQ_QR_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString(INTENT_EXTRA_KEY_QR_SCAN);
            //将扫描出的信息显示出来
            id.setText(scanResult);
        } else if (requestCode == ADD_SCENE) {
            initScene();
        }
    }

    /**
     * 开始扫码
     */
    private void startQrCode() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                Toast.makeText(this, "请至权限中心打开本应用的相机访问权限", Toast.LENGTH_LONG).show();
            }
            // 申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, Constant.REQ_PERM_CAMERA);
            return;
        }
        // 二维码扫码
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, REQ_QR_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.REQ_PERM_CAMERA:
                // 摄像头权限申请
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获得授权
                    startQrCode();
                } else {
                    // 被禁止授权
                    Toast.makeText(this, "请至权限中心打开本应用的相机访问权限", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    /**
     * Called when the checked state of a compound button has changed.
     *
     * @param buttonView The compound button view whose state has changed.
     * @param isChecked  The new checked state of buttonView.
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.iBeacon:
                setting.setIBeaconBroadcast(isChecked);
                break;
            case R.id.bluetooth_accept:
                setting.setBluetoothAccept(isChecked);
                break;
            case R.id.reserved_item_check_box:
                setting.setReserved(isChecked);
                break;
            case R.id.six_box:
                setting.setEnabledSix(isChecked);
                break;
            case R.id.seven_box:
                setting.setEnabledSeven(isChecked);
                break;
            case R.id.eight_box:
                setting.setEnabledEight(isChecked);
                break;
            case R.id.nine_box:
                setting.setEnabledNine(isChecked);
                break;
            case R.id.ten_box:
                setting.setEnabledTen(isChecked);
                break;
        }
    }

    @Override
    public void update(int command) {
        if (command == 2) {
            isAccept=true;
            connection.controller.stopAdvertising();
            connection.controller.stopScanLe();
            handle.post(() -> {
                        loadingDialog.dismiss();
                        createDialog(R.drawable.message, "配置成功");
                    }
            );
        }
    }
}