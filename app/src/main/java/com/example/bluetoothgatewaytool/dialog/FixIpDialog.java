package com.example.bluetoothgatewaytool.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.bluetoothgatewaytool.R;
import com.example.bluetoothgatewaytool.activty.SettingActivity;

/**
 * @author 章可政
 * @date 2021/7/27 18:58
 */
public class FixIpDialog extends Dialog  {

    public FixIpDialog(@NonNull Context context,int res) {
        super(context,res);
    }


    public static class Builder implements View.OnClickListener{
        private EditText ip_edittext;

        private EditText mask_edittext;

        private EditText gateway_edittext;
        private FixIpDialog dialog;

        private Button save;

        private Button cancel;

        private Context context;
        public Builder(Context context) {
            this.context = context;
        }
        public FixIpDialog created() {
            dialog = new FixIpDialog(context, R.style.fix_ip_dialog);
            View layout = View.inflate(context,R.layout.ip_pop_view, null);
            ip_edittext=layout.findViewById(R.id.ip_edittext);
            mask_edittext=layout.findViewById(R.id.mask_edittext);
            gateway_edittext=layout.findViewById(R.id.gateway_edittext);
            save=layout.findViewById(R.id.save);
            cancel=layout.findViewById(R.id.cancel);
            if (SettingActivity.getSetting().getFixIp()!=null){
                ip_edittext.setText(SettingActivity.getSetting().getFixIp());
            }
            if (SettingActivity.getSetting().getMask()!=null){
                mask_edittext.setText(SettingActivity.getSetting().getMask());
            }
            if (SettingActivity.getSetting().getGateway()!=null){
                gateway_edittext.setText(SettingActivity.getSetting().getGateway());
            }
            dialog.setContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            dialog.setCancelable(false);
            setBackgroundAlpha(0.7f,context);
            save.setOnClickListener(this);
            cancel.setOnClickListener(this);
            return dialog;
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.save:
                    SettingActivity.getSetting().setFixIp(ip_edittext.getText().toString());
                    SettingActivity.getSetting().setMask(mask_edittext.getText().toString());
                    SettingActivity.getSetting().setGateway(gateway_edittext.getText().toString());
                    dialog.dismiss();
                    setBackgroundAlpha(1,context);
                    break;
                case R.id.cancel:
                    dialog.dismiss();
                    setBackgroundAlpha(1,context);
                    break;
            }
        }
    }
    /**
     * 设置背景颜色
     * @param bgAlpha
     */
    public  static void setBackgroundAlpha(float bgAlpha, Context mContext) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }
}
