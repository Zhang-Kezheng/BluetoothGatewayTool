package com.example.bluetoothgatewaytool.activty;

import android.annotation.SuppressLint;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseSettings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.bluetoothgatewaytool.Application;
import com.example.bluetoothgatewaytool.R;
import com.example.bluetoothgatewaytool.bluetooth.BluetoothData;
import com.example.bluetoothgatewaytool.bluetooth.CustomCallback;
import com.example.bluetoothgatewaytool.model.User;
import com.example.bluetoothgatewaytool.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PasswordActivity extends BaseActivity implements View.OnClickListener {
    private final CustomCallback usernameCallback=new CustomCallback();
    private final CustomCallback passwordCallback=new CustomCallback();

    private EditText password_username;

    private EditText password_password;

    private EditText password_repassword;

    private Button password_save;

    private ImageView password_back;

    private boolean isAccept;
    /**
     * 设置布局文件
     */
    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_password);
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
        password_username=findViewById(R.id.password_username);
        password_password=findViewById(R.id.password_password);
        password_repassword=findViewById(R.id.password_repassword);
        password_save=findViewById(R.id.password_save);
        password_back=findViewById(R.id.password_back);
    }

    /**
     * 实例化控件之后的操作
     */
    @Override
    public void afterInitView() {
        password_save.setOnClickListener(this);
        password_back.setOnClickListener(this);
        createLoadingDialog("正在修改请稍后");
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
            case R.id.password_back:
                this.finish();
                break;
            case R.id.password_save:
                String username= password_username.getText().toString();
                String password= password_password.getText().toString();
                String repassword= password_repassword.getText().toString();
                if (StringUtils.isEmpty(username)){
                    showToast("用户名不能为空", Toast.LENGTH_SHORT);
                    break;
                }
                if (StringUtils.isEmpty(password)){
                    showToast("密码不能为空", Toast.LENGTH_SHORT);
                    break;
                }
                if (StringUtils.isEmpty(repassword)){
                    showToast("确认密码不能为空", Toast.LENGTH_SHORT);
                    break;
                }
                if (username.length()<4||username.length()>13){
                    showToast("用户名长度不正确", Toast.LENGTH_SHORT);
                    break;
                }
                if (password.length()<4||password.length()>13){
                    showToast("密码长度不正确", Toast.LENGTH_SHORT);
                    break;
                }
                if (repassword.length()<4||repassword.length()>13){
                    showToast("确认密码长度不正确", Toast.LENGTH_SHORT);
                    break;
                }
                if (!password.equals(repassword)){
                    showToast("你输入的两次密码不一样，请重新输入",Toast.LENGTH_SHORT);
                    break;
                }
                User user=User.find(User.class,1);
                user.setUsername(username);
                user.setPassword(password);
                user.save();
                Application.applicationData.setUser(user);
                BluetoothData data = BluetoothData.getBluetoothData(Constant.UPDATE_HEADER,(byte) 0x01, username.getBytes(StandardCharsets.UTF_8));
                BluetoothData data1 = BluetoothData.getBluetoothData(Constant.UPDATE_HEADER,(byte) 0x02, password.getBytes(StandardCharsets.UTF_8));
                List<byte[]> bytes=new ArrayList<>();
                bytes.add(data.parseData());
                bytes.add(data1.parseData());
                connection.controller.startScanLe();
                connection.controller.startAdvertising(bytes);
//                Map<byte[], AdvertiseCallback> map=new HashMap<>();
//                map.put(data.parseData(),usernameCallback);
//                map.put(data1.parseData(),passwordCallback);
//                connection.controller.startAdvertising(map);
                loadingDialog.show();
                handle.postDelayed(() -> {
                    loadingDialog.dismiss();
                    connection.controller.stopAdvertising();
                    connection.controller.stopScanLe();
                        if (isAccept){
                            isAccept=false;
                        }else {
                            createDialog(R.drawable.error,"未收到回复");
                        }
                },5000);
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
        if (command==3){
            isAccept=true;
            loadingDialog.dismiss();
            connection.controller.stopAdvertising();
            connection.controller.stopScanLe();
            handle.post(() -> createDialog(R.drawable.message,"修改成功"));
        }
    }
}