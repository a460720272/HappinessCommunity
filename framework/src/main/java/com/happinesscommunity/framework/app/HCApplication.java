package com.happinesscommunity.framework.app;

import android.app.Application;

public abstract class HCApplication extends Application {

    protected abstract void startSplahActivity();

    @Override
    public void onCreate() {
        super.onCreate();
        startSplahActivity();
    }
}
