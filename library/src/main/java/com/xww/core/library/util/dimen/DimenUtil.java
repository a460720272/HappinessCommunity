package com.xww.core.library.util.dimen;

import android.util.DisplayMetrics;

import com.xww.core.library.app.AppConfiguration;


/**
 * 功能：获取屏幕的尺寸（宽、高）
 *
 * @author : xww
 * @created at : 19-3-17
 * @time : 下午7:30
 */
public final class DimenUtil {
    public static int getScreenWidth() {
        return getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return getDisplayMetrics().heightPixels;
    }

    private static DisplayMetrics getDisplayMetrics() {
        return AppConfiguration.getInstance().getAppContext().getResources().getDisplayMetrics();
    }
}
