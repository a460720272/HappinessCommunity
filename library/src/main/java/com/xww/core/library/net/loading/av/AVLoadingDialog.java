package com.xww.core.library.net.loading.av;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.wang.avi.AVLoadingIndicatorView;
import com.xww.core.library.R;
import com.xww.core.library.util.dimen.DimenUtil;

import java.util.ArrayList;

/**
 * 功能： 弹出一个对话框，用于网络请求时发起，显示请求进度
 * 对话框的进度条动画使用　AVLoadingIndicatorView
 *
 * @author : xww
 * @created at : 19-3-17
 * @time : 下午7:29
 */
public final class AVLoadingDialog {

    private static final int LOADER_SIZE_SCALE = 8;//宽度缩放比例
    private static final int LOADER_OFFSET_SCALE = 10;//高度缩放比例
    //将 dialog　存储到数组中
    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();
    //默认的 AVLoadingIndicatorView 样式
    private static final String DEFAULT_LOADER = AVLoaderStyle.BallSpinFadeLoaderIndicator.name();

    public static void showLoading(Context context, String type) {
        //使用兼容包的 dialog
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.AVLoadingDialog);
        final AVLoadingIndicatorView avLoadingIndicatorView = AVLoaderCreator.create(type, context);
        dialog.setContentView(avLoadingIndicatorView);

        int deviceWidth = DimenUtil.getScreenWidth();//屏幕宽
        int deviceHeight = DimenUtil.getScreenHeight();//屏幕高
        final Window dialogWindow = dialog.getWindow();

        if (dialogWindow != null) {
            final WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            //进行缩放
            lp.width = deviceWidth / LOADER_SIZE_SCALE;
            lp.height = deviceHeight / LOADER_SIZE_SCALE;
            lp.height = lp.height + deviceHeight / LOADER_OFFSET_SCALE;
            //显示居中
            lp.gravity = Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.show();
    }

    /**
     * 显示对话框，使用默认类型
     */
    public static void showLoading(Context context) {
        showLoading(context, DEFAULT_LOADER);
    }

    /**
     * 显示对话框，并设置 style
     *
     * @param type 枚举类型 style
     */
    public static void showLoading(Context context, Enum<AVLoaderStyle> type) {
        showLoading(context, type.name());
    }

    /**
     * 停止显示对话框
     */
    public static void stopLoading() {
        for (AppCompatDialog dialog : LOADERS) {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.cancel();
                }
            }
        }
    }
}
