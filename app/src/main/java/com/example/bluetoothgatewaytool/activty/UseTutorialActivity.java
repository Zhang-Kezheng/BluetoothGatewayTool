package com.example.bluetoothgatewaytool.activty;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.bluetoothgatewaytool.R;

public class UseTutorialActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_use_tutorial);
        ImageView back = findViewById(R.id.back);
        Button go_login = findViewById(R.id.go_login);
        back.setOnClickListener(this);
        go_login.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
            case R.id.go_login:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                this.finish();
                break;
        }
    }
}