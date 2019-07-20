package com.xww.core.library.net.rest;

import android.content.Context;
import android.text.TextUtils;

import com.xww.core.library.net.loading.av.AVLoaderStyle;
import com.xww.core.library.net.loading.av.AVLoadingDialog;
import com.xww.core.library.net.rest.callback.IError;
import com.xww.core.library.net.rest.callback.IFailure;
import com.xww.core.library.net.rest.callback.IRequest;
import com.xww.core.library.net.rest.callback.ISuccess;
import com.xww.core.library.net.rest.download.DownloadTask;
import com.xww.core.library.util.log.LoggerUtil;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * 功能：refrofit 网络请求框架
 *
 * @author : xww
 * @created at : 19-3-12
 * @time : 下午3:19
 */
public class RestClient {

    //存放请求参数的容器
    private final WeakHashMap<String, Object> PARAMS = RestClientCreator.getParams();
    //请求的 url
    private final String URL;
    //加载进度显示需要的上下文
    private final Context CONTEXT;
    //加载进度的样式
    private final Enum<AVLoaderStyle> STYLE;
    //请求开启与结束回调
    private final IRequest REQUEST;
    //请求成功回调
    private final ISuccess SUCCESS;
    //请求失败回调
    private final IFailure FAILURE;
    //请求错误回调
    private final IError ERROR;
    //请求体
    private final RequestBody BODY;
    //文件上传
    private final File FILE;
    //文件完整名字
    private final String FILE_NAME;
    //文件路径
    private final String FILE_DIR;
    //文件扩展名
    private final String EXTENSION_NAME;

    RestClient(String url, Context context, Enum<AVLoaderStyle> style, WeakHashMap<String, Object> params, IRequest request, ISuccess success, IFailure failure,
               IError error, RequestBody body, File file, String fileName, String fileDir, String extensionName) {
        this.PARAMS.putAll(params);
        this.URL = url;
        this.CONTEXT = context;
        this.STYLE = style;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.FILE = file;
        this.FILE_NAME = fileName;
        this.FILE_DIR = fileDir;
        this.EXTENSION_NAME = extensionName;
    }

    public static RestClientBuilder Builder() {
        return new RestClientBuilder();
    }

    /**
     * 发起一个请求
     *
     * @param method 　请求方式
     */
    private void request(HttpMethod method) {
        startRequest();

        final RestClientService service = RestClientCreator.getRestService();
        Call<String> call = null;

        switch (method) {
            case GET:
                call = service.get(URL, PARAMS);
                break;
            case POST:
                call = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                call = service.postRaw(URL, BODY);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                call = service.putRaw(URL, BODY);
                break;
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                /*
                 * 上传文件请求
                 */
                final RequestBody requestBody = MultipartBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part file = MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                call = service.upload(URL, file);
                break;
            default:
                break;
        }

        if (call != null) {
            //异步方式进行请求
            call.enqueue(getRestCallback());
        }
    }

    public final void get() {
        request(HttpMethod.GET);
    }

    public final void post() {
        //POST 方式，RequestBody　必须为空
        if (BODY == null) {
            request(HttpMethod.POST);
        } else {
            // POST_RAW 方式，请求参数　PARAMS　必须为空
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.POST_RAW);
        }
    }

    public final void put() {
        if (BODY == null) {
            request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }

    public final void download() {
        //传入需要的参数，开始下载文件
        new DownloadTask(PARAMS, URL, REQUEST, SUCCESS, FAILURE, ERROR, FILE_NAME, FILE_DIR, EXTENSION_NAME).start();
    }

    /**
     * 返回请求回调，并且执行
     * REQUEST、SUCCESS、FAILURE、ERROR 的回调
     *
     * @return 请求回调接口
     */
    private Callback<String> getRestCallback() {
        return new RestCallback(CONTEXT, REQUEST, SUCCESS, FAILURE, ERROR);
    }

    /**
     * 请求开始
     */
    private void startRequest() {
        if (CONTEXT != null) {
            if (STYLE != null) {
                AVLoadingDialog.showLoading(CONTEXT, STYLE);
            } else {
                AVLoadingDialog.showLoading(CONTEXT);
            }
        }

        if (REQUEST != null) {
            //下载开始时回调
            REQUEST.requestStart();
        }
    }
}
