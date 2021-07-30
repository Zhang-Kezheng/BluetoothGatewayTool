package com.example.bluetoothgatewaytool.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.bluetoothgatewaytool.R;

/**
 * @author 章可政
 * @date 2021/7/23 17:04
 */
public class CustomDialog extends Dialog {

    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }


    public static class Builder {
        private Context context;
        private String message;
        private int logo_view_id;
        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }



        public Builder setLogoView(int logo_view_id){
            this.logo_view_id=logo_view_id;
            return this;
        }


        public CustomDialog create() {
            // instantiate the dialog with the custom Theme
            final CustomDialog dialog = new CustomDialog(context, R.style.Dialog);
            View layout = View.inflate(context,R.layout.custom_dialog_view, null);
            ImageView logo_view = layout.findViewById(R.id.logo);
            logo_view.setImageResource(logo_view_id);
            TextView message_view=layout.findViewById(R.id.message);
            message_view.setText(message);
            dialog.setContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return dialog;
        }
    }
}
