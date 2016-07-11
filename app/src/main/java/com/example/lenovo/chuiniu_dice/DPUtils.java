package com.example.lenovo.chuiniu_dice;

import android.content.Context;

/**
 * Created by Lenovo on 2016/7/3.
 */
public class DPUtils {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
