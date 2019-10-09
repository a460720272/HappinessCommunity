package com.xww.core.library.util.activity;

import android.app.Activity;

import java.util.Iterator;
import java.util.Stack;

/**
 * 功能： activity 管理类
 *
 * @author : xww
 * @created at : 19-3-21
 * @time : 下午8:52
 */
public final class ActivityUtil {
    private ActivityUtil() {
    }

    private static final class Holder {
        private final static ActivityUtil INSTANCE = new ActivityUtil();
        private final static Stack<Activity> ACTIVITY_STACK = new Stack<>();
    }

    private Stack<Activity> getActivityStack() {
        return Holder.ACTIVITY_STACK;
    }

    public static ActivityUtil getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 添加 Activity 到堆栈
     */
    public void addActivity(Activity activity) {
        getActivityStack().add(activity);
    }

    /**
     * 获取栈顶 Activity（堆栈中最后一个压入的）
     */
    public Activity getTopActivity() {
        return getActivityStack().lastElement();
    }

    /**
     * 结束栈顶 Activity（堆栈中最后一个压入的）
     */
    public void finishTopActivity() {
        Activity activity = getActivityStack().lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定类名的 Activity
     */
    public void finishActivity(Class<?> clazz) {
        Iterator iterator = getActivityStack().iterator();
        while (iterator.hasNext()) {
            Activity activity = (Activity) iterator.next();
            if (clazz.equals(activity.getClass())) {
                iterator.remove();
                activity.finish();
            }
        }
    }

    /**
     * 结束所有 Activity
     */
    @SuppressWarnings("WeakerAccess")
    public void finishAllActivity() {
        for (int i = 0, size = getActivityStack().size(); i < size; i++) {
            Activity activity = getActivityStack().get(i);
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
        getActivityStack().clear();
    }

    /**
     * 退出应用程序
     */
    public void appExit() {
        try {
            finishAllActivity();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
        }
    }

    /**
     * 结束指定的 Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            getActivityStack().remove(activity);
            activity.finish();
        }
    }

    /**
     * 得到指定类名的 Activity
     */
    public Activity getActivity(Class<?> cls) {
        for (Activity activity : getActivityStack()) {
            if (activity.getClass().equals(cls)) {
                return activity;
            }
        }
        return null;
    }
}
