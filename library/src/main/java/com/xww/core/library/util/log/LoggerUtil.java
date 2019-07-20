package com.xww.core.library.util.log;


import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * 功能：打印日志工具类
 * 引入第三方库 logger
 *
 * @author : xww
 * @created at : 19-3-8
 * @time : 下午10:08
 */
public final class LoggerUtil {
    //调试版本
    public static final int DEBUG = -1;
    //发布版本
    public static final int RELEASE = 1;
    // VERSION 初始化值为 0
    private static int VERSION = 0;

    /**
     * 必须调用 init 方法来初始化
     *
     * @param version log 版本
     */
    public static void init(int version) {
        VERSION = version;
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static void logV(String msg) {
        checkVersion();
        if (VERSION == DEBUG)
            Logger.v(msg);
    }

    public static void logD(String msg) {
        checkVersion();
        if (VERSION == DEBUG)
            Logger.d(msg);
    }

    public static void logI(String msg) {
        checkVersion();
        if (VERSION == DEBUG)
            Logger.i(msg);
    }

    public static void logW(String msg) {
        checkVersion();
        if (VERSION == DEBUG)
            Logger.w(msg);
    }

    public static void logE(String msg) {
        checkVersion();
        if (VERSION == DEBUG)
            Logger.e(msg);
    }

    public static void logJson(String json) {
        checkVersion();
        if (VERSION == DEBUG)
            Logger.json(json);
    }

    public static void logXml(String xml) {
        checkVersion();
        if (VERSION == DEBUG)
            Logger.xml(xml);
    }

    private static void checkVersion() {
        if (VERSION == 0)
            throw new NullPointerException("call LoggerUtil init()");
    }

    public static int getVersion() {
        return VERSION;
    }
}
