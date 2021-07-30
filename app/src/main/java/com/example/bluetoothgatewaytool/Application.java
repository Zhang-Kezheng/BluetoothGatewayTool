package com.example.bluetoothgatewaytool;


import android.annotation.SuppressLint;
import android.os.Environment;
import com.clj.fastble.BleManager;
import com.example.bluetoothgatewaytool.model.ApplicationData;
import com.example.bluetoothgatewaytool.model.User;
import org.litepal.LitePal;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;


/**
 * @author 章可政
 * @date 2021/7/20 15:31
 */
public class Application extends android.app.Application {
    public static final ApplicationData applicationData=new ApplicationData();

    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        BleManager.getInstance().init(this);
        BleManager.getInstance()
                .enableLog(true)
                .setReConnectCount(1, 5000)
                .setOperateTimeout(5000);
        User user = User.find(User.class, 1);
        if (user==null){
            user=new User();
            user.setId(0);
            user.setUsername("admin");
            user.setPassword("admin");
            user.save();
        }
        applicationData.setUser(user);
        //记录崩溃信息
        final Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            //获取崩溃时的UNIX时间戳
            long timeMillis = System.currentTimeMillis();
            //将时间戳转换成人类能看懂的格式，建立一个String拼接器
            @SuppressLint("SimpleDateFormat") StringBuilder stringBuilder = new StringBuilder(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timeMillis)));
            stringBuilder.append(":\n");
            //获取错误信息
            stringBuilder.append(throwable.getMessage());
            stringBuilder.append("\n");
            //获取堆栈信息
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            throwable.printStackTrace(pw);
            stringBuilder.append(sw);
            //这就是完整的错误信息了，你可以拿来上传服务器，或者做成本地文件保存等等等等
            String errorLog = stringBuilder.toString();
            @SuppressLint("SdCardPath") String path ="/mnt/sdcard/";
            File file=new File(path+"log.text");
            if (!file.exists()){
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(errorLog.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //最后如何处理这个崩溃，这里使用默认的处理方式让APP停止运行
            assert defaultHandler != null;
            defaultHandler.uncaughtException(thread, throwable);
        });
    }

}
