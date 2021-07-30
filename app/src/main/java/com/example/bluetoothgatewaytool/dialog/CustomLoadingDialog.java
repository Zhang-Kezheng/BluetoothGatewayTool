package com.example.bluetoothgatewaytool.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.bluetoothgatewaytool.R;

/**
 * @author 章可政
 * @date 2021/7/24 20:46
 */
public class CustomLoadingDialog extends Dialog {


    public CustomLoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        private Context context;
        private String message;
        public Builder(Context context) {
            this.context = context;
        }

        public CustomLoadingDialog.Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public CustomLoadingDialog.Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }




        public CustomLoadingDialog create() {
            // instantiate the dialog with the custom Theme
            final CustomLoadingDialog dialog = new CustomLoadingDialog(context, R.style.Dialog);
            View layout = View.inflate(context,R.layout.loading_view, null);
            TextView message_view=layout.findViewById(R.id.message);
            message_view.setText(message);
            dialog.setContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            dialog.setCancelable(false);
            return dialog;
        }
    }
}
