package com.xww.core.library.net.rest;

import com.xww.core.library.app.AppConfiguration;

import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 功能： RestClient 创建者类，用于创建
 * Retrofit、OkHttp、Service 等实例
 *
 * @author : xww
 * @created at : 19-3-12
 * @time : 下午4:10
 */
public final class RestClientCreator {

    /**
     * 构建 Params 请求容器，用于存放请求参数
     */
    private static final class ParamsHolder {
        private static final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    }

    /**
     * 构建全局的 Retrofit
     */
    private static final class RestClientHolder {
        //创建 Retrofit 实例，必须设置 baseurl ，且以 / 结尾
        private final static String BASE_URL = AppConfiguration.getInstance().getApiHost();
        private static final Retrofit REST_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpClientHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    private static final class OkHttpClientHolder {
        private static final int TIMEOUT = 30;
        private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 构建全局的 RestClientService
     */
    private static final class RestServiceHolder {
        //创建 RestClientService 接口的实例
        private static final RestClientService REST_SERVICE = RestClientHolder.REST_CLIENT.create(RestClientService.class);
    }

    public static WeakHashMap<String, Object> getParams() {
        return ParamsHolder.PARAMS;
    }

    public static RestClientService getRestService() {
        return RestServiceHolder.REST_SERVICE;
    }
}
