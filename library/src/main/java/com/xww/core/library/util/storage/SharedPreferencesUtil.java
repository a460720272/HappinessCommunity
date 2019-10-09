package com.xww.core.library.util.storage;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * @author xww
 * @desciption : SharedPreferences 工具类
 * @date 2019/9/21
 * @time 12:18
 */
public class SharedPreferencesUtil {

    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "shared_data";

    public static String getFileName() {
        return FILE_NAME;
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 保存数据
     */
    public static void put(Context context, String key, Object object) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        editor.apply();
    }

    /**
     * 得到保存数据的方法
     */
    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = getSharedPreferences(context);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }

    /**
     * 移除某个 key 值已经对应的值
     */
    public static void remove(Context context, String key) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * 清除所有数据
     */
    public static void clear(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.clear();
        editor.apply();
    }

    /**
     * 查询某个 key 是否已经存在
     */
    public static boolean contains(Context context, String key) {
        return getSharedPreferences(context).contains(key);
    }

    /**
     * 返回所有的键值对
     */
    public static Map<String, ?> getAll(Context context) {
        return getSharedPreferences(context).getAll();
    }
}
