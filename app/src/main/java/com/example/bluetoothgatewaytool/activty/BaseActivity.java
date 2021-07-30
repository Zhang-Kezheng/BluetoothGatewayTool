package com.example.bluetoothgatewaytool.activty;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.*;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.bluetoothgatewaytool.dialog.CustomDialog;
import com.example.bluetoothgatewaytool.dialog.CustomLoadingDialog;
import com.example.bluetoothgatewaytool.bluetooth.BluetoothService;
import com.example.bluetoothgatewaytool.bluetooth.BluetoothServiceConnection;
import com.example.bluetoothgatewaytool.bluetooth.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 章可政
 * @date 2021/7/19 22:23
 */
public abstract class BaseActivity extends AppCompatActivity implements Observer {
    private final String[] permissions = new String[]{Manifest.permission.INTERNET, Manifest.permission.VIBRATE,
            Manifest.permission.CAMERA, Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private final List<String> mPermissionList = new ArrayList<>();
    private static final int PERMISSION_REQUEST = 1;
    public Handler handle=new Handler(Looper.getMainLooper());
    /**
     * 是否全屏
     */
    protected boolean isFullScreen = false;

    /**
     * 蓝牙服务链接
     */
    protected BluetoothServiceConnection connection;

    protected CustomLoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setFullScreen(isFullScreen);
        connectService();
        setContentLayout();
        beforeInitView();
        initView();
        afterInitView();
        checkPermission();
    }

    /**
     * 连接服务
     */
    protected  void connectService(){
        Intent intent = new Intent(this, BluetoothService.class);
        connection = new BluetoothServiceConnection(this);
        bindService(intent, connection, BIND_AUTO_CREATE);

    }

    /**
     * 是否全屏
     */
    public void setFullScreen(boolean fullScreen) {
        if (fullScreen) {
            Objects.requireNonNull(getSupportActionBar()).hide();
        }
    }

    public void showToast(String message,int duration){
        Toast.makeText(this,message,duration).show();
    }
    /**
     * 设置布局文件
     */
    public abstract void setContentLayout();

    /**
     * 在实例化控件之前的逻辑操作
     */
    public abstract void beforeInitView();

    /**
     * 实例化控件
     */
    public abstract void initView();

    /**
     * 实例化控件之后的操作
     */
    public abstract void afterInitView();

    /**
     * onClick方法的封装
     */
    public abstract void onClickEvent(View v);

    /**
     * 检查所需权限，如没有授予，申请授权
     */
    private void checkPermission() {
        mPermissionList.clear();
        //判断哪些权限未授予
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        if (!mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST);
        }
    }
    public void startActivity(Class<?> clazz){
        Intent intent=new Intent(this,clazz);
        startActivity(intent);
    }
    // 带参数跳转
    protected void startActivity(Class<?> targetClass, String key, String value) {
        Intent intent = new Intent(this, targetClass);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    // 带请求码跳转
    protected void startActivity(Class<?> targetClass, int requestCode) {
        Intent intent = new Intent(this, targetClass);
        startActivityForResult(intent, requestCode);
    }

    // 带参数和请求码跳转
    protected void startActivity(Class<?> targetClass, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, targetClass);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        connection.controller.getSubject().removeObserver(this);
    }

    public  void createDialog(int res, String message){
        CustomDialog.Builder builder=new CustomDialog.Builder(this);
        builder.setLogoView(res);
        builder.setMessage(message);
        CustomDialog customDialog = builder.create();
        customDialog.show();
        handle.postDelayed(customDialog::dismiss,2000);
    }
    public  void createLoadingDialog(String message){
        CustomLoadingDialog.Builder builder=new CustomLoadingDialog.Builder(this);
        builder.setMessage(message);
        loadingDialog=builder.create();
    }
}
