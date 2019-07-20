package com.xww.core.library.app;

import android.content.Context;
import android.os.Handler;

/**
 * 功能：获取app参数配置
 *
 * @author : xww
 * @created at : 19-3-17
 * @time : 下午9:08
 */
public final class AppConfiguration {

    private static final class Holder {
        private static final AppConfiguration configuration = new AppConfiguration();
    }

    public static AppConfiguration getInstance() {
        return Holder.configuration;
    }

    /**
     * 根据 key 返回配置文件内容，并检查是否为空值
     */
    private Object getAppConfigs(String configKey) {
        final Object obj = AppConfigurator.getConfiguration().getAppConfigs().get(configKey);
        if (obj == null)
            throw new NullPointerException(configKey + " is null,call configurator");
        return obj;
    }

    public final Context getAppContext() {
        return (Context) getAppConfigs(ConfigKeys.App_Context.name());
    }

    public final String getApiHost() {
        return (String) getAppConfigs(ConfigKeys.Api_Host.name());
    }

    public final String getBingApi() {
        return (String) getAppConfigs(ConfigKeys.Bing_Api.name());
    }

    public final String getProvinceApi() {
        return (String) getAppConfigs(ConfigKeys.Province_Api.name());
    }

    public final String getApiKey() {
        return (String) getAppConfigs(ConfigKeys.Api_Key.name());
    }

    public final Handler getHandler() {
        return (Handler) getAppConfigs(ConfigKeys.Handler.name());
    }
}
