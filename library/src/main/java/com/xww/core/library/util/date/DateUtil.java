package com.xww.core.library.util.date;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能：日期／时间／星期工具类
 *
 * @author : xww
 * @created at : 19-3-11
 * @time : 下午9:56
 */
@SuppressLint("SimpleDateFormat")
public final class DateUtil {
    /**
     * 根据传入的格式，返回特定的日期＼时间＼星期等
     *
     * @param format eg： yyyy-mm-dd HH:MM:ss　获取年月日（2019-03-11 21:56:33）
     *               eg： EEEE 获取星期（星期一）
     */
    public static String getDate(String format) {
        return new SimpleDateFormat(format).format(new Date(System.currentTimeMillis()));
    }
}
