package com.example.bluetoothgatewaytool.activty;
import android.annotation.SuppressLint;
import android.bluetooth.le.AdvertiseCallback;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.nfc.cardemulation.CardEmulation;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.bluetoothgatewaytool.Application;
import com.example.bluetoothgatewaytool.R;
import com.example.bluetoothgatewaytool.bluetooth.BluetoothData;
import com.example.bluetoothgatewaytool.bluetooth.CustomCallback;
import com.example.bluetoothgatewaytool.model.User;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 登录页面
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 用户名
     */
    private EditText main_username;

    /**
     * 密码
     */
    private EditText main_password;

    /**
     * 登录按钮
     */
    private Button main_login_button;

    private ConstraintLayout use_tutorial;
    private boolean isAccept;

    /**
     * 设置布局文件
     */
    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_main);
    }


    /**
     * 在实例化控件之前的逻辑操作
     */
    @Override
    public void beforeInitView() {
    }
    /**
     * 实例化控件
     */
    @Override
    public void initView() {
        main_username=findViewById(R.id.main_username);
        main_password=findViewById(R.id.main_password);
        main_login_button=findViewById(R.id.main_login_button);
        use_tutorial=findViewById(R.id.use_tutorial);
    }

    /**
     * 实例化控件之后的操作
     */
    @Override
    public void afterInitView() {
        main_login_button.setOnClickListener(this);
        use_tutorial.setOnClickListener(this);
        User user = User.find(User.class, 1);
        if (user==null){
            user=new User();
            user.setId(1);
            user.setUsername("admin");
            user.setPassword("admin");
            user.save();
            Application.applicationData.setUser(user);
        }
        if (user.isLogin()){
            main_username.setText(user.getUsername());
        }
        createLoadingDialog("正在登录请稍后");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            LocationManager locationManager  = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                Toast.makeText(this, "用户打开定位服务", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "用户关闭定位服务", Toast.LENGTH_SHORT).show();
            }
        }
    }
    /**
     * onClick方法的封装
     *
     * @param v
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClickEvent(View v) {
        switch (v.getId()) {
            case R.id.main_login_button:
                if (!connection.controller.isOpen()){
                    connection.controller.enable();
                    break;
                }
                String username = main_username.getText().toString();
                String password = main_password.getText().toString();
                Application.applicationData.getUser().setUsername(username);
                Application.applicationData.getUser().setPassword(password);
                if (username.length()>13||username.length()<4){
                    createDialog(R.drawable.error,"输入4-13位");
                    return;
                }
                if (password.length()>13||password.length()<4) {
                    createDialog(R.drawable.error,"输入4-13位");
                    return;
                }
                loadingDialog.show();
                BluetoothData data = BluetoothData.getBluetoothData(Constant.LOGIN_HEADER,(byte) 0x01, username.getBytes(StandardCharsets.UTF_8));
                BluetoothData data1 = BluetoothData.getBluetoothData(Constant.LOGIN_HEADER,(byte) 0x02, password.getBytes(StandardCharsets.UTF_8));
//                Map<byte[], AdvertiseCallback> map=new HashMap<>();
//                map.put(data.parseData(),usernameCallback);
//                map.put(data1.parseData(),passwordCallback);
                List<byte[]> bytes=new ArrayList<>();
                bytes.add(data.parseData());
                bytes.add(data1.parseData());
                connection.controller.startAdvertising(bytes);
                connection.controller.startScanLe();
                handle.postDelayed(() -> {
                        connection.controller.stopScanLe();
                        connection.controller.stopAdvertising();
                        if (isAccept){
                            isAccept=false;
                        }else {
                            loadingDialog.dismiss();
                            createDialog(R.drawable.error,"未收到回复");
                        }
                },5000);
                break;
            case R.id.use_tutorial:
                startActivity(UseTutorialActivity.class);
                this.finish();
                break;
            default:
                break;
        }
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

    @Override
    public void update(int command) {
        connection.controller.stopAdvertising();
        connection.controller.stopScanLe();
        handle.post(() -> {
            isAccept=true;
            loadingDialog.dismiss();
            if (command==0){
                showToast("登录成功", Toast.LENGTH_LONG);
                Application.applicationData.getUser().setLogin(true);
                Application.applicationData.getUser().save();
                startActivity(SettingActivity.class);
                finish();
            }else if (command==1){
                showToast("登录失败，用户名或密码错误",Toast.LENGTH_LONG);
            }
        });
    }
}