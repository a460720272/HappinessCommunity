package com.xww.core.library.net.loading.av;

import android.content.Context;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;

/**
 * 功能： AVLoadingIndicatorView 创建
 * 并且为其设置 Indicator 动画
 *
 * @author : xww
 * @created at : 19-3-17
 * @time : 下午7:27
 */
public final class AVLoaderCreator {

    private static final WeakHashMap<String, Indicator> LOADING_MAP = new WeakHashMap<>();

    /**
     * 创建 AVLoadingIndicatorView 实例
     *
     * @param type Indicator 的样式类型，其值为 AVLoaderStyle 枚举类 String 类型的名称
     */
    static AVLoadingIndicatorView create(String type, Context context) {
        final AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);
        //惰性设置 style，如果 Indicator 没有被回收，则直接复用之前的 Indicator
        if (LOADING_MAP.get(type) == null) {
            final Indicator indicator = getIndicator(type);
            LOADING_MAP.put(type, indicator);
        }
        avLoadingIndicatorView.setIndicator(LOADING_MAP.get(type));
        return avLoadingIndicatorView;
    }

    /**
     * 创建 AVLoadingIndicatorView 中用于显示动画的 Indicator 实例
     */
    @SuppressWarnings("ConstantConditions")
    private static Indicator getIndicator(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        final StringBuilder drawableClassName = new StringBuilder();
        if (!name.contains(".")) {
            final String defaultPackageName = AVLoadingIndicatorView.class.getPackage().getName();
            drawableClassName.append(defaultPackageName)
                    .append(".indicators")
                    .append(".");
        }
        drawableClassName.append(name);

        try {
            //使用反射来创建 Indicator 实例
            //优化 Indicator 默认创建方式的性能
            final Class<?> drawableClass = Class.forName(drawableClassName.toString());
            return (Indicator) drawableClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
