package com.example.bluetoothgatewaytool.util;

import android.animation.ObjectAnimator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

/**
 * @author 章可政
 * @date 2020/10/19 21:42
 */
public class AnimatorUtil {
    public static ObjectAnimator build(ImageView view,float angle){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0.0f, angle);
        animator.setDuration(100);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(1);
        return animator;
    }
}
