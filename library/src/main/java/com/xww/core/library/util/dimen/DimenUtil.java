package com.xww.core.library.util.dimen;

import android.content.Context;
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

    /**
     * @return 屏幕的宽度
     */
    public static int getScreenWidth() {
        return getDisplayMetrics().widthPixels;
    }

    /**
     * @return 屏幕的高度
     */
    public static int getScreenHeight() {
        return getDisplayMetrics().heightPixels;
    }

    private static DisplayMetrics getDisplayMetrics() {
        return AppConfiguration.getInstance().getAppContext().getResources().getDisplayMetrics();
    }

    /*
     * dp 转 px
     */
    public int dp2px(float dp) {
        float scale = getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /*
     * px 转 dp
     */
    public static int px2dp(float px) {
        float scale = getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}
