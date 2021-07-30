package com.example.bluetoothgatewaytool.activty;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.bluetoothgatewaytool.R;
import com.example.bluetoothgatewaytool.model.Scene;
import com.example.bluetoothgatewaytool.util.StringUtils;

public class AddSceneActivity extends BaseActivity implements View.OnClickListener {

    private EditText floor_number;

    private EditText area_number;

    private EditText area_name;

    private EditText x;

    private EditText y;

    private EditText z;

    private Button save;

    private ImageView password_back;
    /**
     * 设置布局文件
     */
    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_add_scene);
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
        floor_number=findViewById(R.id.floor_number);
        area_number=findViewById(R.id.area_number);
        area_name=findViewById(R.id.area_name);
        x=findViewById(R.id.x);
        y=findViewById(R.id.y);
        z=findViewById(R.id.z);
        save=findViewById(R.id.password_save);
        password_back=findViewById(R.id.password_back);
    }

    /**
     * 实例化控件之后的操作
     */
    @Override
    public void afterInitView() {
        save.setOnClickListener(this);
        password_back.setOnClickListener(this);
    }

    /**
     * onClick方法的封装
     *
     * @param v
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClickEvent(View v) {
        switch (v.getId()){
            case R.id.password_save:
                String floor_number_string = floor_number.getText().toString();
                String area_number_string = area_number.getText().toString();
                String area_name_string = area_name.getText().toString();
                String x_string = x.getText().toString();
                String y_string = y.getText().toString();
                String z_string = z.getText().toString();
                if (StringUtils.isEmpty(floor_number_string)){
                    showToast("楼层号不能为空",Toast.LENGTH_SHORT);
                    break;
                }
                if (StringUtils.isEmpty(area_number_string)){
                    showToast("区域编号不能为空",Toast.LENGTH_SHORT);
                    break;
                }
                if (StringUtils.isEmpty(area_name_string)){
                    showToast("区域名字不能为空",Toast.LENGTH_SHORT);
                    break;
                }
                if (StringUtils.isEmpty(x_string)){
                    showToast("x不能为空",Toast.LENGTH_SHORT);
                    break;
                }
                if (StringUtils.isEmpty(y_string)){
                    showToast("y不能为空",Toast.LENGTH_SHORT);
                    break;
                }
                if (StringUtils.isEmpty(z_string)){
                    showToast("z不能为空",Toast.LENGTH_SHORT);
                    break;
                }
                Scene scene=new Scene();
                scene.setName(area_name_string);
                scene.setAreaName(area_name_string);
                scene.setAreaNumber(area_number_string);
                scene.setFloorNumber(floor_number_string);
                scene.setX(Float.parseFloat(x_string));
                scene.setY(Float.parseFloat(y_string));
                scene.setZ(Float.parseFloat(z_string));
                scene.save();
                this.finish();
                break;
            case R.id.password_back:
                finish();
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

    }
}