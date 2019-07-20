package com.xww.core.library.app;

import android.content.Context;
import android.os.Handler;

import com.xww.core.library.util.log.LoggerUtil;

import java.util.HashMap;

/**
 * 功能：app全局参数文件配置
 *
 * @author : xww
 * @created at : 19-3-17
 * @time : 下午8:45
 */
public final class AppConfigurator {

    //保存全局配置文件
    private static final HashMap<Object, Object> APP_CONFIGS = new HashMap<>();
    //全局的 HANDLER
    private static final Handler HANDLER = new Handler();

    private AppConfigurator() {
        APP_CONFIGS.put(ConfigKeys.Handler.name(), HANDLER);
    }

    private final static class Holder {
        private static final AppConfigurator configurator = new AppConfigurator();
    }

    public static AppConfigurator getConfiguration() {
        return Holder.configurator;
    }

    /**
     * 获得整个 app 需要的参数文件
     */
    public final HashMap<Object, Object> getAppConfigs() {
        return APP_CONFIGS;
    }

    public final AppConfigurator init(Context context) {
        APP_CONFIGS.put(ConfigKeys.App_Context.name(), context.getApplicationContext());
        return this;
    }

    public final AppConfigurator withApiHost(String apiHost) {
        APP_CONFIGS.put(ConfigKeys.Api_Host.name(), apiHost);
        return this;
    }

    public final AppConfigurator withBingApi(String bingApi) {
        APP_CONFIGS.put(ConfigKeys.Bing_Api.name(), bingApi);
        return this;
    }

    public final AppConfigurator withProvinceApi(String provinceApi) {
        APP_CONFIGS.put(ConfigKeys.Province_Api.name(), provinceApi);
        return this;
    }

    public final AppConfigurator withApiKey(String apiKey) {
        APP_CONFIGS.put(ConfigKeys.Api_Key.name(), apiKey);
        return this;
    }
}
